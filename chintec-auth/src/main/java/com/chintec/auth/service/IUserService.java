package com.chintec.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.auth.entity.TUser;
import com.chintec.auth.response.UserResponse;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/24 17:13
 */
public interface IUserService extends IService<TUser> {
    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    UserResponse getUserByName(String name);
}
