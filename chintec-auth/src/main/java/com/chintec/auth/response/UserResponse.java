package com.chintec.auth.response;

import com.chintec.auth.entity.TUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/24 17:12
 */
@Data
public class UserResponse extends TUser {
    @ApiModelProperty(value = "用户角色")
    private Set<GrantedAuthority> roles;
}
