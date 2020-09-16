package com.chintec.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.auth.entity.Authority;
import com.chintec.auth.request.AuthorityRequest;
import com.chintec.auth.request.CredentialsRequest;
import com.chintec.common.util.ResultResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
public interface IAuthorityService extends IService<Authority> {
    /**
     * 角色列表查询
     */
    ResultResponse getRoleList(Integer pageSize, Integer currentPage, String role,
                               String status, String searchValue, String sorted);

    /**
     * 新增角色
     *
     * @return
     */
    ResultResponse addRole(AuthorityRequest authorityRequest);

    /**
     * 通过Id查询角色
     *
     * @param id
     * @return
     */
    public ResultResponse queryRole(String id);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    public ResultResponse deleteRole(String id);
}
