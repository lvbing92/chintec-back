package com.chintec.message.service;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/18 12:46
 */
public interface ISmsServices {
    /**
     * 发送短信
     *
     * @param phone
     */
    void sendSms(String phone);

    /**
     * 检验验证码
     *
     * @param phone
     * @param code
     */
    void checkCode(String phone, String code);
}
