package com.chintec.backofiice.controller;

import com.chintec.backofiice.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author rubin
 */
@RestController
public class BackOfficeController {
    @Autowired
    private IAuthService iAuthService;

    @GetMapping("/user")
    public List<Map<String,Object>> getUser(){

        return iAuthService.getUsers();
    }
}
