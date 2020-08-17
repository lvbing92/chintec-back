package com.chintec.backofiice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
/**
 * @author rubin
 */
@FeignClient(value = "chintec-auth",path = "/v1")
public interface IAuthService {
    @GetMapping("auths")
    List<Map<String, Object>> getUsers();
}
