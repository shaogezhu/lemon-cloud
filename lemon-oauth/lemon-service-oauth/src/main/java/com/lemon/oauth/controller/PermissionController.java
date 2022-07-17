package com.lemon.oauth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.service.PermissionService;
import io.github.talelin.core.annotation.PermissionModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PermissionController
 **/
@RestController
@RequestMapping("/cms/permission")
@PermissionModule(value = "权限")
@Validated
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/list")
    public ResponseEntity<List<PermissionDO>> getAll(){
        List<PermissionDO> list = permissionService.list();
        if (list==null) list = Collections.emptyList();
        return ResponseEntity.ok(list);
    }

    @RequestMapping("/update")
    public void updateById(@RequestParam(name = "id") Long id, @RequestParam(name = "name") String name,
                                                 @RequestParam(name = "module") String module, @RequestParam(name = "mount") Boolean mount){
        permissionService.updateById(PermissionDO.builder().id(id).name(name).module(module).mount(mount).build());
    }

    @RequestMapping("/name")
    public ResponseEntity<PermissionDO> getOneByName(String name, String module){
        return ResponseEntity.ok(permissionService.getOne(new QueryWrapper<PermissionDO>().lambda()
                .eq(PermissionDO::getName,name)
                .eq(PermissionDO::getModule,module)));
    }

    @RequestMapping("/insert")
    public void insertOne(String name, String module){
        permissionService.save(PermissionDO.builder().name(name).module(module).build());
    }

}
