package com.lemon.oauth.config;

import com.lemon.oauth.bean.PermissionMetaCollector;
import com.lemon.oauth.core.JwtToken;
import com.lemon.oauth.feign.AuthFeign;
import com.lemon.oauth.feign.CmsUserFeign;
import com.lemon.oauth.feign.LogFeign;
import com.lemon.oauth.feign.PermissionFeign;
import com.lemon.oauth.interceptors.AuthorizeInterceptor;
import com.lemon.oauth.interceptors.AuthorizeVerifyResolverImpl;
import com.lemon.oauth.interceptors.LogInterceptor;
import com.lemon.oauth.interceptors.LoggerImpl;
import com.lemon.oauth.listnter.PermissionHandleListener;
import io.github.talelin.core.token.DoubleJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName AuthConfiguration
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "lemon.auth", name = {"clientId", "secret"})
@EnableConfigurationProperties(value = {ClientProperties.class,CmsProperties.class})
public class AuthConfiguration {

    private DoubleJWT jwt;
    private AuthFeign authFeign;
    private CmsUserFeign userFeign;
    private LogFeign logFeign;
    private ClientProperties properties;
    private PermissionFeign permissionFeign;
    private PermissionMetaCollector metaCollector;

    public AuthConfiguration(AuthFeign authFeign,
                             CmsUserFeign userFeign,
                             LogFeign logFeign,
                             @Lazy DoubleJWT jwter,
                             ClientProperties properties,
                             PermissionFeign permissionFeign,
                             @Lazy PermissionMetaCollector metaCollector) {
        this.authFeign = authFeign;
        this.userFeign = userFeign;
        this.logFeign = logFeign;
        this.permissionFeign = permissionFeign;
        this.metaCollector = metaCollector;
        this.properties = properties;
        this.jwt = jwter;
    }

    @Bean
    @Primary
    public JwtToken jwtToken(){
        try {
            // 查询秘钥
            String key = authFeign.getSecretKey(properties.getClientId(), properties.getSecret());
            // 创建JwtToken
            return new JwtToken(key);
        } catch (Exception e) {
            log.error("初始化JwtToken失败，{}", e.getMessage());
            throw e;
        }
    }


    @Bean
    @Primary
    public AuthorizeVerifyResolverImpl authorizeVerifyResolverImpl(){
        return new AuthorizeVerifyResolverImpl(jwt,userFeign);
    }

    /**
     * 记录每个被 @PermissionMeta 记录的信息，在beans的后置调用
     *
     * @return PermissionMetaCollector
     */
    @Bean
    @Primary
    public PermissionMetaCollector postProcessBeans() {
        return new PermissionMetaCollector();
    }

    @Bean
    @Primary
    public PermissionHandleListener permissionHandleListener(){
        return new PermissionHandleListener(permissionFeign,metaCollector);
    }

    @Bean
    @Primary
    public DoubleJWT jwter() {
        CmsProperties cmsProperties = authFeign.getSecretJwtKey(properties.getClientId(), properties.getSecret());
        String secret = cmsProperties.getTokenSecret();
        Long accessExpire = cmsProperties.getTokenAccessExpire();
        Long refreshExpire = cmsProperties.getTokenRefreshExpire();
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

