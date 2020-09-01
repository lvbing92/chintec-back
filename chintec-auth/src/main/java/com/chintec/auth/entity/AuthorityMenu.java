package com.chintec.auth.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityMenu extends Model<AuthorityMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     */
    private Integer authorityId;

    /**
     * 菜单Id
     */
    private Integer menuId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}