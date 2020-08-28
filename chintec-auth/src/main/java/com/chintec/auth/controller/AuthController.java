package com.chintec.auth.controller;

import com.chintec.auth.service.IUserService;
import com.chintec.common.util.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/17 17:00
 */
@RestController
@RequestMapping("/v1")
public class AuthController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/user")
    public ResultResponse getUser() {
        return ResultResponse.successResponse("获取用户成功");
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/meet")
    public String meet(){
        return "I meet you";
    }

    @GetMapping("/welcome")
    public String welcome(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Welcome " + authentication.getName();
    }

}
