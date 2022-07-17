package com.lemon.oauth.interfaces;

import io.github.talelin.core.annotation.Logger;
import io.github.talelin.core.annotation.PermissionMeta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LoggerResolver
 * 行为日志记录
 **/
public interface LoggerResolver {

    /**
     * 处理
     *
     * @param meta     路由信息
     * @param logger   logger 信息
     * @param request  请求
     * @param response 响应
     */
    void handle(PermissionMeta meta, Logger logger, HttpServletRequest request, HttpServletResponse response);
}
