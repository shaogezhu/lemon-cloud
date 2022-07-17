package com.lemon.goods;

import com.lemon.oauth.annotation.EnableJwtVerification;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GoodsApplication
 **/
@MapperScan("com.lemon.goods.mapper")
@SpringBootApplication(scanBasePackages = {"com.lemon.goods","com.lemon.advice","com.lemon.**.feign"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lemon.**.feign"})
@EnableJwtVerification
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class);
    }
}
