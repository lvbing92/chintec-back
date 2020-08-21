package com.chintec.backofiice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/17 16:28
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BackOfficeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackOfficeApplication.class,args);
    }
}
