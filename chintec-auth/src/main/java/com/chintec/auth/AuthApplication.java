package com.chintec.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/14 10:05
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAuthorizationServer
@MapperScan("com.chintec.auth.mapper")
//@EnableOAuth2Sso
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
