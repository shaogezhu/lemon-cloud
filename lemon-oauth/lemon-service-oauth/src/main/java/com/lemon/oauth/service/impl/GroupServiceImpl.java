package com.lemon.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.enumeration.GroupLevelEnum;
import com.lemon.oauth.bo.GroupPermissionBO;
import com.lemon.oauth.mapper.CmsUserGroupMapper;
import com.lemon.oauth.mapper.GroupMapper;
import com.lemon.oauth.pojo.cms.GroupDO;
import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.pojo.cms.UserGroupDO;
import com.lemon.oauth.service.GroupService;
import com.lemon.oauth.service.PermissionService;
import com.lemon.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GroupServiceImpl
 **/
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CmsUserGroupMapper userGroupMapper;

    @Override
    public List<GroupDO> getUserGroupsByUserId(Long userId) {
        return this.baseMapper.selectGroupsByUserId(userId);
    }

    @Override
    public List<Long> getUserGroupIdsByUserId(Long userId) {
        return this.baseMapper.selectUserGroupIds(userId);
    }

    @Override
    public IPage<GroupDO> getGroupPage(int page, int count) {
        Page<GroupDO> pager = new Page<>(page, count);
        return this.baseMapper.selectPage(pager, null);
    }

    @Override
    public boolean checkGroupExistById(Long id) {
        return this.baseMapper.selectCountById(id) > 0;
    }

    @Override
    public GroupPermissionBO getGroupAndPermissions(Long id) {
        GroupDO group = this.baseMapper.selectById(id);
        List<PermissionDO> permissions = permissionService.getPermissionByGroupId(id);
        return new GroupPermissionBO(group, permissions);
    }

    @Override
    public boolean checkGroupExistByName(String name) {
        QueryWrapper<GroupDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(GroupDO::getName, name);
        return this.baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean checkIsRootByUserId(Long userId) {
        QueryWrapper<UserGroupDO> wrapper = new QueryWrapper<>();
        Long rootGroupId = this.getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        wrapper.lambda().eq(UserGroupDO::getUserId, userId)
                .eq(UserGroupDO::getGroupId, rootGroupId);
        UserGroupDO relation = userGroupMapper.selectOne(wrapper);
        return relation != null;
    }

    @Override
    public boolean deleteUserGroupRelations(Long userId, List<Long> deleteIds) {
        if (deleteIds == null || deleteIds.isEmpty()) {
            return true;
        }
        if (checkIsRootByUserId(userId)) {
            throw new ForbiddenException(10078);
        }
        QueryWrapper<UserGroupDO> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(UserGroupDO::getUserId, userId)
                .in(UserGroupDO::getGroupId, deleteIds);
        return userGroupMapper.delete(wrapper) > 0;
    }

    @Override
    public boolean addUserGroupRelations(Long userId, List<Long> addIds) {
        if (addIds == null || addIds.isEmpty()) {
            return true;
        }
        boolean ok = checkGroupExistByIds(addIds);
        if (!ok) {
            throw new ForbiddenException(10077);
        }
        List<UserGroupDO> relations = addIds.stream().map(it -> new UserGroupDO(userId, it)).collect(Collectors.toList());
        return userGroupMapper.insertBatch(relations) > 0;
    }

    @Override
    public List<Long> getGroupUserIds(Long id) {
        QueryWrapper<UserGroupDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserGroupDO::getGroupId, id);
        List<UserGroupDO> list = userGroupMapper.selectList(wrapper);
        return list.stream().map(UserGroupDO::getUserId).collect(Collectors.toList());
    }

    @Override
    public GroupDO getParticularGroupByLevel(GroupLevelEnum level) {
        if (GroupLevelEnum.USER == level) {
            return null;
        } else {
            QueryWrapper<GroupDO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(GroupDO::getLevel, level.getValue());
            GroupDO groupDO = this.baseMapper.selectOne(wrapper);
            return groupDO;
        }
    }

    @Override
    public Long getParticularGroupIdByLevel(GroupLevelEnum level) {
        GroupDO group = this.getParticularGroupByLevel(level);
        return group == null ? 0L : group.getId();
    }

    private boolean checkGroupExistByIds(List<Long> ids) {
        return ids.stream().allMatch(this::checkGroupExistById);
    }
}
