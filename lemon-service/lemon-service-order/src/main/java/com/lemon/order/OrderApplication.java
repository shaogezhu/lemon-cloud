package com.lemon.order;

import com.lemon.oauth.annotation.EnableJwtVerification;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderApplication
 **/
@MapperScan("com.lemon.order.mapper")
@SpringBootApplication(scanBasePackages = {"com.lemon.order", "com.lemon.advice", "com.lemon.**.feign"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lemon.**.feign"})
@EnableJwtVerification
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
