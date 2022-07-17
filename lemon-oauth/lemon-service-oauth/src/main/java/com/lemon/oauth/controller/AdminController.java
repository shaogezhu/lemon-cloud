package com.lemon.oauth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.oauth.bo.GroupPermissionBO;
import com.lemon.oauth.dto.admin.*;
import com.lemon.oauth.dto.querry.BasePageDTO;
import com.lemon.oauth.dto.user.UpdateUserInfoDTO;
import com.lemon.oauth.pojo.cms.GroupDO;
import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.pojo.cms.UserDO;
import com.lemon.oauth.service.AdminService;
import com.lemon.oauth.service.GroupService;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
import com.lemon.oauth.vo.UserInfoVO;
import com.lemon.vo.CreatedVO;
import com.lemon.vo.DeletedVO;
import com.lemon.vo.UpdatedVO;
import io.github.talelin.core.annotation.AdminRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName AdminController
 **/
@Validated
@RestController
@RequestMapping("/cms/admin")
@PermissionModule(value = "管理员")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private GroupService groupService;

    @AdminRequired
    @GetMapping("/permission")
    @PermissionMeta(value = "查询所有可分配的权限", mount = false)
    public ResponseEntity<Map<String, List<PermissionDO>>> getAllPermissions() {
        return ResponseEntity.ok(adminService.getAllStructuralPermissions());
    }

    @AdminRequired
    @GetMapping("/users")
    @PermissionMeta(value = "查询所有用户", mount = false)
    public ResponseEntity<PageResponseVO<UserInfoVO>> getUsers(
            @Validated QueryUsersDTO dto) {
        IPage<UserDO> iPage = adminService.getUserPageByGroupId(dto.getGroupId(), dto.getCount(), dto.getPage());
        List<UserInfoVO> userInfos = iPage.getRecords().stream().map(user -> {
            List<GroupDO> groups = groupService.getUserGroupsByUserId(user.getId());
            return new UserInfoVO(user, groups);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(PageUtil.build(iPage, userInfos));
    }

    @AdminRequired
    @PutMapping("/user/{id}/password")
    @PermissionMeta(value = "修改用户密码", mount = false)
    public ResponseEntity<UpdatedVO> changeUserPassword(@PathVariable @Positive(message = "{id.positive}") Long id, @RequestBody @Validated ResetPasswordDTO validator) {
        adminService.changeUserPassword(id, validator);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @AdminRequired
    @DeleteMapping("/user/{id}")
    @PermissionMeta(value = "删除用户", mount = false)
    public ResponseEntity<DeletedVO> deleteUser(@PathVariable @Positive(message = "{id.positive}") Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @AdminRequired
    @PutMapping("/user/{id}")
    @PermissionMeta(value = "管理员更新用户信息", mount = false)
    public ResponseEntity<UpdatedVO> updateUser(@PathVariable @Positive(message = "{id.positive}") Long id, @RequestBody @Validated UpdateUserInfoDTO validator) {
        adminService.updateUserInfo(id, validator);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @AdminRequired
    @GetMapping("/group")
    @PermissionMeta(value = "查询所有权限组及其权限", mount = false)
    public ResponseEntity<PageResponseVO<GroupDO>> getGroups(
            @Validated BasePageDTO dto) {
        IPage<GroupDO> iPage = adminService.getGroupPage(dto.getPage(), dto.getCount());
        return ResponseEntity.ok(PageUtil.build(iPage));
    }

    @AdminRequired
    @GetMapping("/group/all")
    @PermissionMeta(value = "查询所有权限组", mount = false)
    public ResponseEntity<List<GroupDO>> getAllGroup() {
        return ResponseEntity.ok(adminService.getAllGroups());
    }

    @AdminRequired
    @GetMapping("/group/{id}")
    @PermissionMeta(value = "查询一个权限组及其权限", mount = false)
    public ResponseEntity<GroupPermissionBO> getGroup(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return ResponseEntity.ok(adminService.getGroup(id));
    }

    @AdminRequired
    @PostMapping("/group")
    @PermissionMeta(value = "新建权限组", mount = false)
    public ResponseEntity<CreatedVO> createGroup(@RequestBody @Validated NewGroupDTO validator) {
        adminService.createGroup(validator);
        return ResponseEntity.ok(new CreatedVO());
    }

    @AdminRequired
    @PutMapping("/group/{id}")
    @PermissionMeta(value = "更新一个权限组", mount = false)
    public ResponseEntity<UpdatedVO> updateGroup(@PathVariable @Positive(message = "{id.positive}") Long id,
                                 @RequestBody @Validated UpdateGroupDTO validator) {
        adminService.updateGroup(id, validator);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @AdminRequired
    @DeleteMapping("/group/{id}")
    @PermissionMeta(value = "删除一个权限组", mount = false)
    public ResponseEntity<DeletedVO> deleteGroup(@PathVariable @Positive(message = "{id.positive}") Long id) {
        adminService.deleteGroup(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @AdminRequired
    @PostMapping("/permission/dispatch")
    @PermissionMeta(value = "分配单个权限", mount = false)
    public ResponseEntity<UpdatedVO> dispatchPermission(@RequestBody @Validated DispatchPermissionDTO validator) {
        adminService.dispatchPermission(validator);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @AdminRequired
    @PostMapping("/permission/dispatch/batch")
    @PermissionMeta(value = "分配多个权限", mount = false)
    public ResponseEntity<UpdatedVO> dispatchPermissions(@RequestBody @Validated DispatchPermissionsDTO validator) {
        adminService.dispatchPermissions(validator);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @AdminRequired
    @PostMapping("/permission/remove")
    @PermissionMeta(value = "删除多个权限", mount = false)
    public ResponseEntity<DeletedVO> removePermissions(@RequestBody @Validated RemovePermissionsDTO validator) {
        adminService.removePermissions(validator);
        return ResponseEntity.ok(new DeletedVO());
    }

}