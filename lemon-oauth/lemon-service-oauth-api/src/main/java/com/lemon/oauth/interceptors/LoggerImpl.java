package com.lemon.oauth.interceptors;

import com.lemon.entity.LocalUser;
import com.lemon.oauth.feign.CmsUserFeign;
import com.lemon.oauth.feign.LogFeign;
import com.lemon.oauth.interfaces.LoggerResolver;
import com.lemon.oauth.pojo.cms.UserDO;
import io.github.talelin.core.annotation.Logger;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LoggerImpl
 **/
@Slf4j
public class LoggerImpl implements LoggerResolver {

    private LogFeign logFeign;

    private CmsUserFeign userFeign;

    public LoggerImpl(LogFeign logFeign, CmsUserFeign userFeign) {
        this.logFeign = logFeign;
        this.userFeign = userFeign;
    }
    /**
     * 日志格式匹配正则
     */
    private static final Pattern LOG_PATTERN = Pattern.compile("(?<=\\{)[^}]*(?=})");

    @Override
    public void handle(PermissionMeta meta, Logger logger, HttpServletRequest request, HttpServletResponse response) {
        String template = logger.template();
        Long userId = LocalUser.getUserId();
        UserDO user = userFeign.getUserById(userId);
        template = this.parseTemplate(template, user, request, response);
        String permission = "";
        if (meta != null) permission = meta.value();
        String username = user.getUsername();
        String method = request.getMethod();
        String path = request.getServletPath();
        Integer status = response.getStatus();
        logFeign.createLog(template, permission, userId, username, method, path, status);
    }

    private String parseTemplate(String template, UserDO user, HttpServletRequest request, HttpServletResponse response) {
        // 调用 get 方法
        Matcher m = LOG_PATTERN.matcher(template);
        while (m.find()) {
            String group = m.group();
            String property = this.extractProperty(group, user, request, response);
            template = template.replace("{" + group + "}", property);
        }
        return template;
    }

    private String extractProperty(String item, UserDO user, HttpServletRequest request, HttpServletResponse response) {
        int i = item.lastIndexOf('.');
        String obj = item.substring(0, i);
        String prop = item.substring(i + 1);
        switch (obj) {
            case "user":
                if (user == null) {
                    return "";
                }
                return BeanUtil.getValueByPropName(user, prop);
            case "request":
                return BeanUtil.getValueByPropName(request, prop);
            case "response":
                return BeanUtil.getValueByPropName(response, prop);
            default:
                return "";
        }
    }
}

