package com.chintec.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.auth.entity.TRole;
import com.chintec.auth.mapper.RoleMapper;
import com.chintec.auth.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/25 15:18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, TRole> implements IRoleService {
}
