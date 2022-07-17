package com.lemon.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.entity.LocalUser;
import com.lemon.enumeration.GroupLevelEnum;
import com.lemon.exception.FailedException;
import com.lemon.exception.ForbiddenException;
import com.lemon.exception.NotFoundException;
import com.lemon.exception.ParameterException;
import com.lemon.oauth.bo.LoginCaptchaBO;
import com.lemon.oauth.configuration.LoginCaptchaProperties;
import com.lemon.oauth.dto.admin.UpdateInfoDTO;
import com.lemon.oauth.dto.user.ChangePasswordDTO;
import com.lemon.oauth.dto.user.RegisterDTO;
import com.lemon.oauth.mapper.CmsUserGroupMapper;
import com.lemon.oauth.mapper.CmsUserMapper;
import com.lemon.oauth.pojo.cms.GroupDO;
import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.pojo.cms.UserDO;
import com.lemon.oauth.pojo.cms.UserGroupDO;
import com.lemon.oauth.service.CmsUserIdentityService;
import com.lemon.oauth.service.CmsUserService;
import com.lemon.oauth.service.GroupService;
import com.lemon.oauth.service.PermissionService;
import com.lemon.oauth.util.CaptchaUtil;
import com.lemon.oauth.vo.LoginCaptchaVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CmsUserServiceImpl
 **/
@Service
public class CmsUserServiceImpl extends ServiceImpl<CmsUserMapper, UserDO> implements CmsUserService {

    @Autowired
    private CmsUserIdentityService userIdentityService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CmsUserGroupMapper userGroupMapper;

    @Autowired
    private LoginCaptchaProperties captchaConfig;

    @Transactional
    @Override
    public UserDO createUser(RegisterDTO dto) {
        boolean exist = this.checkUserExistByUsername(dto.getUsername());
        if (exist) {
            throw new ForbiddenException(10071);
        }
        if (StringUtils.hasText(dto.getEmail())) {
            exist = this.checkUserExistByEmail(dto.getEmail());
            if (exist) {
                throw new ForbiddenException(10076);
            }
        } else {
            // bug 前端如果传入的email为 "" 时，由于数据库中存在""的email，会报duplication错误
            // 所以如果email为blank，必须显示设置为 null
            dto.setEmail(null);
        }
        UserDO user = new UserDO();
        BeanUtils.copyProperties(dto, user);
        this.baseMapper.insert(user);
        if (dto.getGroupIds() != null && !dto.getGroupIds().isEmpty()) {
            checkGroupsValid(dto.getGroupIds());
            checkGroupsExist(dto.getGroupIds());
            List<UserGroupDO> relations = dto.getGroupIds()
                    .stream()
                    .map(groupId -> new UserGroupDO(user.getId(), groupId))
                    .collect(Collectors.toList());
            userGroupMapper.insertBatch(relations);
        } else {
            // id为2的分组为游客分组
            Long guestGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.GUEST);
            UserGroupDO relation = new UserGroupDO(user.getId(), guestGroupId);
            userGroupMapper.insert(relation);
        }
        userIdentityService.createUsernamePasswordIdentity(user.getId(), dto.getUsername(), dto.getPassword());
        return user;
    }

    @Transactional
    @Override
    public UserDO updateUserInfo(UpdateInfoDTO dto) {
        UserDO user = this.getById(LocalUser.getUserId());
        if (StringUtils.hasText(dto.getUsername())) {
            boolean exist = this.checkUserExistByUsername(dto.getUsername());
            if (exist) {
                throw new ForbiddenException(10071);
            }

            boolean changeSuccess = userIdentityService.changeUsername(user.getId(), dto.getUsername());
            if (changeSuccess) {
                user.setUsername(dto.getUsername());
            }
        }

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getAvatar() != null) {
            user.setAvatar(dto.getAvatar());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
        }

        this.baseMapper.updateById(user);
        return user;
    }

    @Override
    public UserDO changeUserPassword(ChangePasswordDTO dto) {
        UserDO user = this.getById(LocalUser.getUserId());
        if (user.getUsername().equals("guest")) {
            throw new ParameterException(10028);
        }
        boolean valid = userIdentityService.verifyUsernamePassword(user.getId(), user.getUsername(), dto.getOldPassword());
        if (!valid) {
            throw new ParameterException(10032);
        }
        valid = userIdentityService.changePassword(user.getId(), dto.getNewPassword());
        if (!valid) {
            throw new FailedException(10011);
        }
        return user;
    }

    @Override
    public List<GroupDO> getUserGroups(Long userId) {
        return groupService.getUserGroupsByUserId(userId);
    }

    @Override
    public List<Map<String, List<Map<String, String>>>> getStructuralUserPermissions(Long userId) {
        List<PermissionDO> permissions = getUserPermissions(userId);
        return permissionService.structuringPermissions(permissions);
    }

    @Override
    public List<PermissionDO> getUserPermissions(Long userId) {
        // 查找用户搜索分组，查找分组下的所有权限
        List<Long> groupIds = groupService.getUserGroupIdsByUserId(userId);
        if (groupIds == null || groupIds.size() == 0) {
            return new ArrayList<>();
        }
        return permissionService.getPermissionByGroupIds(groupIds);
    }

    @Override
    public List<PermissionDO> getUserPermissionsByModule(Long userId, String module) {
        List<Long> groupIds = groupService.getUserGroupIdsByUserId(userId);
        if (groupIds == null || groupIds.size() == 0) {
            return new ArrayList<>();
        }
        return permissionService.getPermissionByGroupIdsAndModule(groupIds, module);
    }

    @Override
    public UserDO getUserByUsername(String username) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDO::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public boolean checkUserExistByUsername(String username) {
        int rows = this.baseMapper.selectCountByUsername(username);
        return rows > 0;
    }

    @Override
    public boolean checkUserExistByEmail(String email) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDO::getEmail, email);
        int rows = this.baseMapper.selectCount(wrapper);
        return rows > 0;
    }

    @Override
    public boolean checkUserExistById(Long id) {
        int rows = this.baseMapper.selectCountById(id);
        return rows > 0;
    }

    @Override
    public IPage<UserDO> getUserPageByGroupId(Page<UserDO> pager, Long groupId) {
        Long rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        return this.baseMapper.selectPageByGroupId(pager, groupId, rootGroupId);
    }

    @Override
    public Long getRootUserId() {
        Long rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        UserGroupDO userGroupDO = null;
        if (rootGroupId != 0) {
            QueryWrapper<UserGroupDO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(UserGroupDO::getGroupId, rootGroupId);
            userGroupDO = userGroupMapper.selectOne(wrapper);
        }
        return userGroupDO == null ? 0 : userGroupDO.getUserId();
    }

    @Override
    public LoginCaptchaVO generateCaptcha() throws Exception {
        String code = CaptchaUtil.getRandomString(CaptchaUtil.RANDOM_STR_NUM);
        String base64String = CaptchaUtil.getRandomCodeBase64(code);
        String tag = CaptchaUtil.getTag(code, captchaConfig.getSecret(), captchaConfig.getIv());
        return new LoginCaptchaVO(tag, "data:image/png;base64," + base64String);
    }

    @Override
    public boolean verifyCaptcha(String captcha, String tag) {
        try {
            LoginCaptchaBO captchaBO = CaptchaUtil.decodeTag(captchaConfig.getSecret(), captchaConfig.getIv(), tag);
            return captcha.equalsIgnoreCase(captchaBO.getCaptcha()) || System.currentTimeMillis() > captchaBO.getExpired();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private void checkGroupsExist(List<Long> ids) {
        for (Long id : ids) {
            if (!groupService.checkGroupExistById(id)) {
                throw new NotFoundException(10023);
            }
        }
    }

    private void checkGroupsValid(List<Long> ids) {
        Long rootGroupId = groupService.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        boolean anyMatch = ids.stream().anyMatch(it -> it.equals(rootGroupId));
        if (anyMatch) {
            throw new ForbiddenException(10073);
        }
    }
}
