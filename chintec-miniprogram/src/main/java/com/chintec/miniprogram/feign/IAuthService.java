package com.chintec.miniprogram.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/17 17:10
 */
@FeignClient(value = "chintec-auth", path = "/v1")
public interface IAuthService {
    @GetMapping("auths")
    List<Map<String, Object>> getUsers();
}
