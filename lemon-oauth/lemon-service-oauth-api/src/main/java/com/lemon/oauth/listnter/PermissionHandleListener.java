package com.lemon.oauth.listnter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.oauth.feign.PermissionFeign;
import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.bean.MetaInfo;
import com.lemon.oauth.bean.PermissionMetaCollector;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PermissionHandleListener
 **/
public class PermissionHandleListener implements ApplicationListener<ContextRefreshedEvent> {

    private PermissionFeign permissionFeign;
    private PermissionMetaCollector metaCollector;

    public PermissionHandleListener(PermissionFeign permissionFeign, PermissionMetaCollector metaCollector) {
        this.permissionFeign = permissionFeign;
        this.metaCollector = metaCollector;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null||
                event.getApplicationContext().getParent().getParent() == null) {//root application context 没有parent，他就是老大.
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            addNewPermissions();
//            removeUnusedPermissions();
        }
    }

    private void addNewPermissions() {
        metaCollector.getMetaMap().values().forEach(meta -> {
            String module = meta.getModule();
            String permission = meta.getPermission();
            createPermissionIfNotExist(permission, module);
        });
    }

    private void removeUnusedPermissions() {
        List<PermissionDO> allPermissions = permissionFeign.getAll();
        Map<String, MetaInfo> metaMap = metaCollector.getMetaMap();
        for (PermissionDO permission : allPermissions) {
            boolean stayedInMeta = metaMap
                    .values()
                    .stream()
                    .anyMatch(meta -> meta.getModule().equals(permission.getModule())
                            && meta.getPermission().equals(permission.getName()));
            if (!stayedInMeta) {
                permission.setMount(false);
                permissionFeign.updateById(permission.getId(),permission.getName(),permission.getModule(),permission.getMount());
            }
        }
    }

    private void createPermissionIfNotExist(String name, String module) {
        QueryWrapper<PermissionDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PermissionDO::getName, name).eq(PermissionDO::getModule, module);
        PermissionDO permission = permissionFeign.getOneByName(name, module);
        if (permission == null) {
            permissionFeign.insertOne(name, module);
        }
        if (permission != null && !permission.getMount()) {
            permission.setMount(true);
            permissionFeign.updateById(permission.getId(), permission.getName(), permission.getModule(),permission.getMount());
        }
    }
}
