package com.lemon.oauth.bean;

import io.github.talelin.core.annotation.*;
import io.github.talelin.core.enumeration.UserLevel;
import io.github.talelin.core.util.AnnotationUtil;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PermissionMetaCollector
 **/
public class PermissionMetaCollector  implements BeanPostProcessor {

    private Map<String, MetaInfo> metaMap = new ConcurrentHashMap<>();

    private Map<String, Map<String, Set<String>>> structuralMeta = new ConcurrentHashMap<>();

    public PermissionMetaCollector() {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    /**
     * 扫描注解信息，并提取
     *
     * @param bean     spring bean
     * @param beanName 名称
     * @return spring bean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Controller controllerAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
        // 非 Controller 类，无需检查权限信息
        if (controllerAnnotation == null) {
            return bean;
        }
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        for (Method method : methods) {
            PermissionMeta permissionMeta = AnnotationUtils.findAnnotation(method,
                    PermissionMeta.class);
            if (permissionMeta != null && permissionMeta.mount()) {
                String permission = permissionMeta.value();
                UserLevel level = AnnotationUtil.findRequired(method.getAnnotations());
                putOneMetaInfo(method, permission, permissionMeta.module(), level);
            }
        }
        return bean;
    }

    private void putOneMetaInfo(Method method, String permission, String module,
                                UserLevel userLevel) {
        if (StringUtils.isEmpty(module)) {
            PermissionModule permissionModule = AnnotationUtils.findAnnotation(
                    method.getDeclaringClass(), PermissionModule.class);
            if (permissionModule != null) {
                module = permissionModule.value();
            }
        }
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();
        String identity = className + "#" + methodName;
        MetaInfo metaInfo = new MetaInfo(permission, module, identity, userLevel);
        metaMap.put(identity, metaInfo);
        this.putMetaIntoStructuralMeta(identity, metaInfo);
    }

    private void putMetaIntoStructuralMeta(String identity, MetaInfo meta) {
        String module = meta.getModule();
        String permission = meta.getPermission();
        // 如果已经存在了该 module，直接向里面增加
        if (structuralMeta.containsKey(module)) {
            Map<String, Set<String>> moduleMap = structuralMeta.get(module);
            // 如果 permission 已经存在
            this.putIntoModuleMap(moduleMap, identity, permission);
        } else {
            // 不存在 该 module，创建该 module
            Map<String, Set<String>> moduleMap = new HashMap<>();
            // 如果 permission 已经存在
            this.putIntoModuleMap(moduleMap, identity, permission);
            structuralMeta.put(module, moduleMap);
        }
    }

    private void putIntoModuleMap(Map<String, Set<String>> moduleMap, String identity,
                                  String auth) {
        if (moduleMap.containsKey(auth)) {
            moduleMap.get(auth).add(identity);
        } else {
            Set<String> eps = new HashSet<>();
            eps.add(identity);
            moduleMap.put(auth, eps);
        }
    }

    /**
     * 获取路由信息map
     *
     * @return 路由信息map
     */
    public Map<String, MetaInfo> getMetaMap() {
        return metaMap;
    }

    public MetaInfo findMeta(String key) {
        return metaMap.get(key);
    }

    public MetaInfo findMetaByPermission(String permission) {
        Collection<MetaInfo> values = metaMap.values();
        MetaInfo[] objects = values.toArray(new MetaInfo[0]);
        for (MetaInfo object : objects) {
            if (object.getPermission().equals(permission)) {
                return object;
            }
        }
        return null;
    }

    public void setMetaMap(Map<String, MetaInfo> metaMap) {
        this.metaMap = metaMap;
    }

    /**
     * 获得结构化路由信息
     *
     * @return 路由信息
     */
    public Map<String, Map<String, Set<String>>> getStructuralMeta() {
        return structuralMeta;
    }

    public void setStructrualMeta(Map<String, Map<String, Set<String>>> structuralMeta) {
        this.structuralMeta = structuralMeta;
    }
}
