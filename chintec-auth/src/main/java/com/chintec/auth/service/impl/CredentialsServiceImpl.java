package com.chintec.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.auth.entity.Credentials;
import com.chintec.auth.entity.CredentialsAuthorities;
import com.chintec.auth.mapper.CredentialsMapper;
import com.chintec.auth.request.CredentialsRequest;
import com.chintec.auth.service.IAuthorityService;
import com.chintec.auth.service.ICredentialsAuthoritiesService;
import com.chintec.auth.service.ICredentialsService;
import com.chintec.common.util.AssertsUtil;
import com.chintec.common.util.PageResultResponse;
import com.chintec.common.util.ResultResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * <p>
 *  用户服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Service
public class CredentialsServiceImpl extends ServiceImpl<CredentialsMapper, Credentials> implements ICredentialsService,Serializable {

    public static final String SORT_A = "A";
    public static final String SORT_D = "D";
    @Autowired
    private ICredentialsAuthoritiesService iCredentialsAuthoritiesService;

    @Override
    public ResultResponse   getUserList(Integer pageSize, Integer currentPage, String role, String status, String searchValue, String sorted) {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        if (StringUtils.isEmpty(sorted)) {
            sorted = SORT_A;
        }
        //
        IPage<Credentials> page = new Page<>(currentPage, pageSize);
        //分页查询
        IPage<Credentials> credentialsPage = this.page(page, new QueryWrapper<Credentials>().lambda()
                .eq(Credentials::getEnabled, 1)
//                .and(!StringUtils.isEmpty(searchValue), s -> s.like(Credentials::getUserName, searchValue).
//                        or().like(Credentials::getCellphone, searchValue).
//                        or().like(Credentials::getEmail, searchValue).
//                        or().like(Credentials::getUserName, searchValue))
                .orderByAsc(SORT_A.equals(sorted), Credentials::getName)
                .orderByDesc(SORT_D.equals(sorted), Credentials::getName));
//返回结果
        PageResultResponse<Credentials> paginationResultDto = new PageResultResponse<>(credentialsPage.getTotal(), currentPage, pageSize);
        paginationResultDto.setTotalPages(credentialsPage.getPages());
        paginationResultDto.setResults(credentialsPage.getRecords());
        return ResultResponse.successResponse(paginationResultDto);
    }

    @Override
    public ResultResponse addUser(CredentialsRequest credentialsRequest) {
        Credentials credentials = new Credentials();
        BeanUtils.copyProperties(credentialsRequest, credentials);
        //添加用户
        boolean creFlag = this.save(credentials);
        AssertsUtil.isTrue(creFlag,"添加用户失败！");
        //添加用户角色关系表信息
        CredentialsAuthorities credentialsAuthorities = new CredentialsAuthorities();
        credentialsAuthorities.setAuthoritiesId(credentialsRequest.getRoleId());
        credentialsAuthorities.setCredentialsId(credentialsRequest.getId());
        boolean creAuthFlag = iCredentialsAuthoritiesService.save(credentialsAuthorities);
        AssertsUtil.isTrue(creAuthFlag,"添加用户失败！");
        return ResultResponse.successResponse("添加用户成功！");
    }

    @Override
    public ResultResponse queryUser(String id) {
        //查询用户
        Credentials credentials =getById(new QueryWrapper<Credentials>().lambda().eq(Credentials::getId,id));
        //查询当前用户角色
        return ResultResponse.successResponse(credentials);
    }

    @Override
    public ResultResponse deleteUser(String id) {
        this.baseMapper.deleteById(new QueryWrapper<Credentials>().lambda().eq(Credentials::getId,id));
        return ResultResponse.successResponse("删除成功");
    }
}