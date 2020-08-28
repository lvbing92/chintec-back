package com.chintec.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.auth.entity.Credentials;
import com.chintec.auth.mapper.CredentialsMapper;
import com.chintec.auth.service.ICredentialsService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Service
public class CredentialsServiceImpl extends ServiceImpl<CredentialsMapper, Credentials> implements ICredentialsService,Serializable {

}
