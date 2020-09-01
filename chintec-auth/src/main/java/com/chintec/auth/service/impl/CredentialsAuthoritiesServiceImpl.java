package com.chintec.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.auth.entity.CredentialsAuthorities;
import com.chintec.auth.mapper.CredentialsAuthoritiesMapper;
import com.chintec.auth.service.ICredentialsAuthoritiesService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jeff·Tang
 * @since 2020-08-26
 */
@Service
public class CredentialsAuthoritiesServiceImpl extends ServiceImpl<CredentialsAuthoritiesMapper, CredentialsAuthorities> implements ICredentialsAuthoritiesService, Serializable {

}