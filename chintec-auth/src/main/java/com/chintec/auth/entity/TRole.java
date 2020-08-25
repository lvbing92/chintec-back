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
@ApiModel(value = "Role", description = "角色信息")
public class TRole implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "角色类型")
    private String roleType;
    @ApiModelProperty(value = "角色类型")
    private String isDeleted;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "更新人Id")
    private String updateById;
    @ApiModelProperty(value = "更新人名称")
    private String updateByName;
}
