package com.chintec.auth.controller;

import com.chintec.common.util.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/25 15:05
 */
@RestController
@RequestMapping("/v1")
public class MiniProgramController {

    @GetMapping(value = "/login")
    public ResultResponse miniLogin(){
    return ResultResponse.successResponse("小程序登录成功");
    }
}
