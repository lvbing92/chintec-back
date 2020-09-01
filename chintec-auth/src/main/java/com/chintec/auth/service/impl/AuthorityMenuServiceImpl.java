package com.chintec.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.auth.entity.AuthorityMenu;
import com.chintec.auth.mapper.AuthorityMenuMapper;
import com.chintec.auth.service.IAuthorityMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Service
public class AuthorityMenuServiceImpl extends ServiceImpl<AuthorityMenuMapper, AuthorityMenu> implements IAuthorityMenuService {

}