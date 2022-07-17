package com.lemon.oauth;

import com.lemon.oauth.annotation.EnableJwtVerification;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OauthApplication
 **/
@MapperScan("com.lemon.oauth.mapper")
@SpringBootApplication(scanBasePackages = {"com.lemon.oauth","com.lemon.advice","com.lemon.**.feign"})
@EnableDiscoveryClient
@EnableJwtVerification
@EnableFeignClients(basePackages = {"com.lemon.**.feign"})
public class OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class);
    }
}
