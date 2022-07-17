package com.lemon.oauth.config;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.lemon.oauth.core.JwtToken;
import com.lemon.oauth.interceptors.LogInterceptor;
import com.lemon.oauth.interceptors.LoginInterceptor;
import com.lemon.oauth.interceptors.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName MvcConfiguration
 **/
public class MvcConfiguration implements WebMvcConfigurer {

    private JwtToken jwtToken;
    private ClientProperties properties;
    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;
    @Autowired
    private LogInterceptor logInterceptor;

    public MvcConfiguration(@Lazy JwtToken jwtToken, AuthorizeInterceptor authInterceptor, ClientProperties properties) {
        this.jwtToken = jwtToken;
        this.properties = properties;
        this.authorizeInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，并得到拦截器注册器（微信用户的拦截器）
        InterceptorRegistration wxRegistration = registry.addInterceptor(new LoginInterceptor(jwtToken));
        // 注册拦截器，并得到拦截器注册器（管理端用户的拦截器）
        InterceptorRegistration cmsRegistration = registry.addInterceptor(authorizeInterceptor);
        // 判断用户是否配置了拦截路径，如果没配置走默认，就是拦截 /**
        if(!CollectionUtils.isEmpty(properties.getIncludeFilterPaths())){
            wxRegistration.addPathPatterns(properties.getIncludeFilterPaths());
            cmsRegistration.addPathPatterns(properties.getIncludeFilterPaths());
        }
        // 判断用户是否配置了放行路径，如果没配置就是空
        if(!CollectionUtils.isEmpty(properties.getExcludeFilterPaths())){
            wxRegistration.excludePathPatterns(properties.getExcludeFilterPaths());
            cmsRegistration.excludePathPatterns(properties.getExcludeFilterPaths());
        }
        //添加日志拦截器
        registry.addInterceptor(logInterceptor);
    }
}
