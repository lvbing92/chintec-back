package com.chintec.miniprogram.service;

import com.chintec.common.util.ResultResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此接口提供两个方法
 * 一个是调用支付宝支付接口
 * 一个是支付宝支付后回调接口的方法
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/26 16:49
 */
public interface IAliPayService {
    /**
     * 方法调用阿里统一支付接口
     *
     * @return r
     */
    ResultResponse aliPay();

    /**
     * 阿里支付的回调接口
     * 成功支付或失败支付
     *
     * @param response re
     * @param request  re
     */
    void aliNotify(HttpServletResponse response, HttpServletRequest request);
}
