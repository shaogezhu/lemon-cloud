package com.lemon.oauth.configuration;

import com.lemon.oauth.bean.PermissionMetaCollector;
import com.lemon.oauth.config.ClientProperties;
import com.lemon.oauth.config.CmsProperties;
import com.lemon.oauth.core.JwtToken;
import com.lemon.oauth.feign.CmsUserFeign;
import com.lemon.oauth.feign.LogFeign;
import com.lemon.oauth.feign.PermissionFeign;
import com.lemon.oauth.interceptors.AuthorizeInterceptor;
import com.lemon.oauth.interceptors.AuthorizeVerifyResolverImpl;
import com.lemon.oauth.interceptors.LogInterceptor;
import com.lemon.oauth.interceptors.LoggerImpl;
import com.lemon.oauth.listnter.PermissionHandleListener;
import io.github.talelin.core.token.DoubleJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName JwtConfig
 **/
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(value = {ClientProperties.class,CmsProperties.class})
public class JwtConfig {

    @Value("${lemon.security.jwt-key}")
    private String jwtKey;

    private CmsUserFeign userFeign;
    private LogFeign logFeign;
    private PermissionFeign permissionFeign;
    private PermissionMetaCollector metaCollector;
    private DoubleJWT jwter;
    private CmsProperties properties;

    public JwtConfig(CmsUserFeign userFeign,
                     LogFeign logFeign,
                     PermissionFeign permissionFeign,
                     @Lazy PermissionMetaCollector metaCollector,
                     @Lazy DoubleJWT jwter,
                     CmsProperties properties) {
        this.userFeign = userFeign;
        this.logFeign = logFeign;
        this.metaCollector = metaCollector;
        this.jwter = jwter;
        this.properties = properties;
        this.permissionFeign = permissionFeign;
    }

    @Bean
    @Primary
    public JwtToken jwtToken(){
        return new JwtToken(jwtKey);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthorizeVerifyResolverImpl authorizeVerifyResolverImpl(){
        return new AuthorizeVerifyResolverImpl(jwter,userFeign);
    }

    @Bean
    public PermissionMetaCollector postProcessBeans() {
        return new PermissionMetaCollector();
    }

    @Bean
    public PermissionHandleListener permissionHandleListener(){
        return new PermissionHandleListener(permissionFeign,metaCollector);
    }

    @Bean
    @Primary
    public DoubleJWT jwter() {
        String secret = properties.getTokenSecret();
        Long accessExpire = properties.getTokenAccessExpire();
        Long refreshExpire = properties.getTokenRefreshExpire();
        if (accessExpire == null) {
            // 一个小时
            accessExpire = 60 * 60L;
        }
        if (refreshExpire == null) {
            // 一个月
            refreshExpire = 60 * 60 * 24 * 30L;
        }
        return new DoubleJWT(secret, accessExpire, refreshExpire);
    }

    @Bean
    public AuthorizeInterceptor authInterceptor() {
        return new AuthorizeInterceptor();
    }

    @Bean
    public LoggerImpl loggerImpl(){
        return new LoggerImpl(logFeign,userFeign);
    }

    @Bean
    public LogInterceptor logInterceptor(){
        return new LogInterceptor();
    }

}