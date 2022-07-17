package com.lemon.payment;

import com.lemon.oauth.annotation.EnableJwtVerification;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PaymentApplication
 **/
@SpringBootApplication(scanBasePackages = {"com.lemon.payment","com.lemon.advice","com.lemon.**.feign"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lemon.**.feign"})
@EnableJwtVerification
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class);
    }
}
