package com.chintec.auth.controller;

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
    @GetMapping("auths")
    public List<Map<String, Object>> getUsers() {
        Map<String, Object> userMap = new HashMap<>(4);
        userMap.put("name", "张三");
        userMap.put("id", 1);
        userMap.put("age", 20);
        Map<String, Object> userMap1 = new HashMap<>(4);
        userMap1.put("name", "李四");
        userMap1.put("id", 2);
        userMap1.put("age", 18);
        return Arrays.asList(userMap, userMap1);
    }
}
