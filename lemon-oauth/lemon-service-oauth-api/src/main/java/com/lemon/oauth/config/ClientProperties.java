package com.lemon.oauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ClientProperties
 **/
@Data
@ConfigurationProperties("lemon.auth")
public class ClientProperties {
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 客户端秘钥
     */
    private String secret;
    /**
     * 拦截器拦截路径
     */
    private List<String> includeFilterPaths;
    /**
     * 拦截器放行路径
     */
    private List<String> excludeFilterPaths;
}
