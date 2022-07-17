package com.lemon.oauth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.oauth.dto.admin.UpdateInfoDTO;
import com.lemon.oauth.dto.user.ChangePasswordDTO;
import com.lemon.oauth.dto.user.RegisterDTO;
import com.lemon.oauth.pojo.cms.GroupDO;
import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.pojo.cms.UserDO;
import com.lemon.oauth.vo.LoginCaptchaVO;

import java.util.List;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CmsUserService
 **/
public interface CmsUserService extends IService<UserDO> {

    /**
     * 新建用户
     *
     * @param validator 新建用户校验器
     * @return 被创建的用户
     */
    UserDO createUser(RegisterDTO validator);

    /**
     * 更新用户
     *
     * @param validator 更新用户信息用户校验器
     * @return 被更新的用户
     */
    UserDO updateUserInfo(UpdateInfoDTO validator);

    /**
     * 修改用户密码
     *
     * @param validator 修改密码校验器
     * @return 被修改密码的用户
     */
    UserDO changeUserPassword(ChangePasswordDTO validator);

    /**
     * 获得用户的所有分组
     *
     * @param userId 用户id
     * @return 所有分组
     */
    List<GroupDO> getUserGroups(Long userId);

    /**
     * 获得用户所有权限
     *
     * @param userId 用户id
     * @return 权限
     */
    List<Map<String, List<Map<String, String>>>> getStructuralUserPermissions(Long userId);

    /**
     * 获得用户所有权限
     *
     * @param userId 用户id
     * @return 权限
     */
    List<PermissionDO> getUserPermissions(Long userId);


    /**
     * 获得用户在模块下的所有权限
     *
     * @param userId 用户id
     * @param module 权限模块
     * @return 权限
     */
    List<PermissionDO> getUserPermissionsByModule(Long userId, String module);


    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    UserDO getUserByUsername(String username);

    /**
     * 根据用户名检查用户是否存在
     *
     * @param username 用户名
     * @return true代表存在
     */
    boolean checkUserExistByUsername(String username);


    /**
     * 根据用户名检查用户是否存在
     *
     * @param email 邮箱
     * @return true代表存在
     */
    boolean checkUserExistByEmail(String email);

    /**
     * 根据用户id检查用户是否存在
     *
     * @param id 用户名
     * @return true代表存在
     */
    boolean checkUserExistById(Long id);

    /**
     * 根据分组id分页获取用户数据
     *
     * @param pager   分页
     * @param groupId 分组id
     * @return 数据页
     */
    IPage<UserDO> getUserPageByGroupId(Page<UserDO> pager, Long groupId);

    /**
     * 获取超级管理员的id
     *
     * @return 超级管理员的id
     */
    Long getRootUserId();

    /**
     * 生成无状态的登录验证码
     *
     * @return 验证码
     */
    LoginCaptchaVO generateCaptcha() throws Exception;

    /**
     * 校验登录验证码
     *
     * @return 结果
     */
    boolean verifyCaptcha(String captcha, String tag);
}
