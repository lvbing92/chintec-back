package com.chintec.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TMenu extends Model<TMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String menuName;

    /**
     * 父Id
     */
    private String parentId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人Id
     */
    private Integer updateById;

    /**
     * 更新人名称
     */
    private String updateByName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
