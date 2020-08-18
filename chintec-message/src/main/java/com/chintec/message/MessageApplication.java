package com.chintec.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/18 13:24
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }
}
