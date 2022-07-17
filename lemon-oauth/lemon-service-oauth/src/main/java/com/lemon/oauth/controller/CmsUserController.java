package com.lemon.oauth.controller;

import com.lemon.entity.LocalUser;
import com.lemon.exception.NotFoundException;
import com.lemon.exception.ParameterException;
import com.lemon.oauth.configuration.LoginCaptchaProperties;
import com.lemon.oauth.dto.admin.UpdateInfoDTO;
import com.lemon.oauth.dto.user.ChangePasswordDTO;
import com.lemon.oauth.dto.user.LoginDTO;
import com.lemon.oauth.dto.user.RegisterDTO;
import com.lemon.oauth.pojo.cms.GroupDO;
import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.pojo.cms.UserDO;
import com.lemon.oauth.service.CmsUserIdentityService;
import com.lemon.oauth.service.CmsUserService;
import com.lemon.oauth.service.GroupService;
import com.lemon.oauth.vo.LoginCaptchaVO;
import com.lemon.oauth.vo.UserInfoVO;
import com.lemon.oauth.vo.UserPermissionVO;
import com.lemon.vo.CreatedVO;
import com.lemon.vo.UpdatedVO;
import io.github.talelin.core.annotation.AdminRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.core.annotation.RefreshRequired;
import io.github.talelin.core.token.DoubleJWT;
import io.github.talelin.core.token.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CmsUserController
 **/
@RestController
@RequestMapping("/cms/user")
@PermissionModule(value = "用户")
@Validated
public class CmsUserController {


    @Autowired
    private CmsUserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CmsUserIdentityService userIdentityService;

    @Autowired
    private DoubleJWT jwter;

    @Autowired
    private LoginCaptchaProperties captchaConfig;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @AdminRequired
    public ResponseEntity<CreatedVO> register(@RequestBody @Validated RegisterDTO validator) {
        userService.createUser(validator);
        return ResponseEntity.ok(new CreatedVO());
    }

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public Tokens login(@RequestBody @Validated LoginDTO validator, @RequestHeader(value = "Tag", required = false) String tag) {
        if (captchaConfig.getEnabled()) {
            if (!StringUtils.hasText(validator.getCaptcha()) || !StringUtils.hasText(tag)) {
                throw new ParameterException(10090);
            }
            if (!userService.verifyCaptcha(validator.getCaptcha(), tag)) {
                throw new ParameterException(10260);
            }
        }
        UserDO user = userService.getUserByUsername(validator.getUsername());
        if (user == null) {
            throw new NotFoundException(10021);
        }
        boolean valid = userIdentityService.verifyUsernamePassword(
                user.getId(),
                user.getUsername(),
                validator.getPassword());
        if (!valid) {
            throw new ParameterException(10031);
        }
        return jwter.generateTokens(user.getId());
    }

    @PostMapping("/captcha")
    public ResponseEntity<LoginCaptchaVO> userCaptcha() throws Exception {
        if (captchaConfig.getEnabled()) {
            return ResponseEntity.ok(userService.generateCaptcha());
        }
        return ResponseEntity.ok(new LoginCaptchaVO());
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    @LoginRequired
    public ResponseEntity<UpdatedVO> update(@RequestBody @Validated UpdateInfoDTO validator) {
        userService.updateUserInfo(validator);
        return ResponseEntity.ok(new UpdatedVO());
    }

    /**
     * 修改密码
     */
    @PutMapping("/change_password")
    @LoginRequired
    public ResponseEntity<UpdatedVO> updatePassword(@RequestBody @Validated ChangePasswordDTO validator) {
        userService.changeUserPassword(validator);
        return ResponseEntity.ok(new UpdatedVO());
    }

    /**
     * 刷新令牌
     */
    @GetMapping("/refresh")
    @RefreshRequired
    public ResponseEntity<Tokens> getRefreshToken() {
        Long userId = LocalUser.getUserId();
        return ResponseEntity.ok(jwter.generateTokens(userId));
    }

    /**
     * 查询拥有权限
     */
    @GetMapping("/permissions")
    @LoginRequired
    public ResponseEntity<UserPermissionVO> getPermissions() {
        Long userId = LocalUser.getUserId();
        boolean admin = groupService.checkIsRootByUserId(userId);
        List<Map<String, List<Map<String, String>>>> permissions = userService.getStructuralUserPermissions(userId);
        UserDO user = userService.getById(userId);
        UserPermissionVO userPermissions = new UserPermissionVO(user, permissions);
        userPermissions.setAdmin(admin);
        return ResponseEntity.ok(userPermissions);
    }

    /**
     * 查询自己信息
     */
    @LoginRequired
    @GetMapping("/information")
    public ResponseEntity<UserInfoVO> getInformation() {
        Long userId = LocalUser.getUserId();
        List<GroupDO> groups = groupService.getUserGroupsByUserId(userId);
        UserDO user = userService.getById(userId);
        return ResponseEntity.ok(new UserInfoVO(user, groups));
    }

    @RequestMapping("/detail")
    public ResponseEntity<UserDO> getUserById(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(userService.getById(userId));
    }

    @RequestMapping("/permissions/id")
    public ResponseEntity<List<PermissionDO>> getPermissionsById(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(userService.getUserPermissions(userId));
    }

    @RequestMapping("/check")
    public ResponseEntity<Boolean> checkIsRootByUserId(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(groupService.checkIsRootByUserId(userId));
    }

}
