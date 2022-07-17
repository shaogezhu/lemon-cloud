package com.lemon.coupon;

import com.lemon.oauth.annotation.EnableJwtVerification;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponApplication
 **/
@MapperScan("com.lemon.coupon.mapper")
@SpringBootApplication(scanBasePackages = {"com.lemon.coupon", "com.lemon.advice", "com.lemon.**.feign"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lemon.**.feign"})
@EnableJwtVerification  //自定义注解，开启拦截器
public class CouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class);
    }
}
