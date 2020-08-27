package com.chintec.miniprogram.service.impl;

import com.chintec.common.util.ResultResponse;
import com.chintec.miniprogram.service.IAliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/26 16:55
 */
@Service
@Slf4j
public class AliPayServiceImpl implements IAliPayService {
    @Override
    public ResultResponse aliPay() {
        return null;
    }

    @Override
    public void aliNotify(HttpServletResponse response, HttpServletRequest request) {

    }
}
