package com.lemon.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LocalUser
 **/
public class LocalUser {
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void set(Long uid, Integer scope) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", uid);
        map.put("scope", scope);
        LocalUser.threadLocal.set(map);
    }

    public static void set(Long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", uid);
        LocalUser.threadLocal.set(map);
    }

    public static Long getUserId() {
        Map<String, Object> map = LocalUser.threadLocal.get();
        return (Long) map.get("user");
    }

    public static Integer getScope() {
        Map<String, Object> map = LocalUser.threadLocal.get();
        Integer scope = (Integer)map.get("scope");
        return scope;
    }

    public static void clear() {
        LocalUser.threadLocal.remove();
    }
}