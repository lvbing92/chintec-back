package com.chintec.miniprogram.controller;

import com.chintec.miniprogram.feign.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/17 17:14
 */
@RestController
public class MiniController {
    @Autowired
    private IAuthService iAuthService;

    @GetMapping("mini")
    public List<Map<String, Object>> getUsers() {
        return iAuthService.getUsers();
    }
}
