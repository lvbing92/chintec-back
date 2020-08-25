package com.chintec.auth.controller;

import com.chintec.auth.service.IUserService;
import com.chintec.common.util.ResultResponse;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ResultResponse userLogin(){
        return ResultResponse.successResponse("后端登录成功");
    }

    @GetMapping("/user")
    public ResultResponse getUser() {
        return ResultResponse.successResponse(iUserService.getUser());
    }
}
