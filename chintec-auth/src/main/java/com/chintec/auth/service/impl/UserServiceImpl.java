package com.chintec.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.auth.entity.TUser;
import com.chintec.auth.mapper.UserMapper;
import com.chintec.auth.response.UserResponse;
import com.chintec.auth.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/24 17:14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, TUser> implements IUserService {
    @Override
    public UserResponse getUserByName(String name) {
        UserResponse userResponse = new UserResponse();
        TUser user = getOne(new QueryWrapper<TUser>().lambda().eq(TUser::getUserName,name));
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }


}
