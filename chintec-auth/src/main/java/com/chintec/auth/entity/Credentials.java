package com.chintec.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jeff·Tang
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Credentials extends Model<Credentials> {

    private static final long serialVersionUID = 1L;

    /**
     * 凭证id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否可用
     */
    private Boolean enabled;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 版本号
     */
    private Integer version;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
