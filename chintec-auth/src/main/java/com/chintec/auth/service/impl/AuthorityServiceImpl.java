package com.chintec.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.auth.entity.Authority;
import com.chintec.auth.entity.Credentials;
import com.chintec.auth.mapper.AuthorityMapper;
import com.chintec.auth.service.IAuthorityService;
import com.chintec.common.util.ResultResponse;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  角色服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService, Serializable {

    @Override
    public ResultResponse getRoleList() {
        return null;
    }

    @Override
    public ResultResponse addRole() {
        return null;
    }

    @Override
    public ResultResponse queryRole(String id) {
        return null;
    }

    @Override
    public ResultResponse deleteRole(String id) {
        this.baseMapper.deleteById(new QueryWrapper<Authority>().lambda().eq(Authority::getId,id));
        return ResultResponse.successResponse("删除成功");
    }
}
