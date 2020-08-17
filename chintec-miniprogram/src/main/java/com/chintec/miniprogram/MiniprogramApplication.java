package com.chintec.miniprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/17 16:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MiniprogramApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniprogramApplication.class, args);
    }
}
