package com.chintec.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.auth.entity.TUserRole;
import com.chintec.auth.mapper.UserRoleMapper;
import com.chintec.auth.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/25 15:24
 */
@Service
public class UserRoleImpl extends ServiceImpl<UserRoleMapper, TUserRole> implements IUserRoleService {
}
