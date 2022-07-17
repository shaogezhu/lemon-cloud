package com.lemon.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName RateLimitConfig
 * @Description 定义KeyResolver,用于限制通过网关的请求。可以根据ip、用户、请求uri进行限流
 **/
@Configuration
public class RateLimitConfig {

    /**
     * 根据IP进行限流，这里采用IP限流，需要配置redis
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
            }
        };
        // JDK8 的Lambda写法(可以参考):
        // return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**
     * 根据用户名进行限流
     */
//    @Bean
    public KeyResolver userKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return exchange.getPrincipal().map(Principal::getName);// 获取用户
            }
        };
    }

    /**
     * 根据请求uri进行限流
     */
//    @Bean
    public KeyResolver urlKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getURI().getPath());// 获取请求URI
            }
        };
    }
}
