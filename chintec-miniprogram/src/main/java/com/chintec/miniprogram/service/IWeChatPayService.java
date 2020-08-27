package com.chintec.miniprogram.service;

import com.chintec.common.util.ResultResponse;
import com.chintec.miniprogram.entity.request.BeanOrder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此接口提供两个接口 分别用于微信支付和回调业务
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/26 15:09
 */
public interface IWeChatPayService {
    /**
     * 此方法用于微信支付接口调用和签名 返回给前台一个签名用于发起微信支付
     * 此方法一般用于app和小程序调用微信支付
     *
     * @param beanOrder 支付请求类包含订单 客户端 支付方式信息
     * @param request   httpServiletRequest
     * @return ResultResponse
     */
    ResultResponse weChatPay(BeanOrder beanOrder, HttpServletRequest request);

    /**
     * 此方法用于微信支付后的回调方法
     * 一般是成功或是失败
     * 失败就会就改变订单状态为支付失败状态，让用户再次支付
     * 成功改变订单状态为已支付
     *
     * @param request  re
     * @param response re
     */
    void wxNotify(HttpServletRequest request, HttpServletResponse response);
}
