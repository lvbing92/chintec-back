package com.chintec.auth.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/24 17:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserRole", description = "用户角色关系信息")
public class TUserRole implements Serializable {

    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "角色Id")
    private Long roleId;
}
