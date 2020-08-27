package com.chintec.auth.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class CredentialsAuthorities extends Model<CredentialsAuthorities> {

    private static final long serialVersionUID = 1L;

    /**
     * 凭证id
     */
    private Long credentialsId;

    /**
     * 权限id
     */
    private Long authoritiesId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
