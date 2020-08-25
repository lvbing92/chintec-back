package com.chintec.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.auth.entity.TUser;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/24 17:13
 */
public interface IUserService extends IService<TUser> {
    TUser getUser();
}
