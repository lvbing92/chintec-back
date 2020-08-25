package com.chintec.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/24 10:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TUser", description = "用户信息")
public class TUser implements Serializable {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "秘钥")
    private String ecSalt;
    @ApiModelProperty(value = "添加时间")
    private String addTime;
    @ApiModelProperty(value = "最后登录时间")
    private String lastLogin;
    @ApiModelProperty(value = "最后登录Ip")
    private String lastIp;
//    @ApiModelProperty(value = "创建时间")
//    private String createTime;
//    @ApiModelProperty(value = "更新时间")
//    private String updateTime;
//    @ApiModelProperty(value = "更新人Id")
//    private String updateById;
//    @ApiModelProperty(value = "更新人名称")
//    private String updateByName;
}
