package com.chintec.message.service.impl;

import com.alibaba.alicloud.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.chintec.common.util.AssertsUtil;
import com.chintec.message.service.ISmsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/18 12:47
 */
@Slf4j
@Service
public class SmsServiceImpl implements ISmsServices {
    @Autowired
    private ISmsService ismsService;
    @Value("${spring.cloud.alicloud.sms.sign}")
    private String sign;
    @Value("${spring.cloud.alicloud.sms.template-code}")
    private String templateCode;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendSms(String phone) {
        ValueOperations<String, String> stringObjectValueOperations = redisTemplate.opsForValue();
        Long expire = redisTemplate.getExpire(phone);
        AssertsUtil.isTrue(!StringUtils.isEmpty(expire) && expire > 240, "请勿重复发送验证码");
        Integer code = getCode();
        SendSmsResponse sendSmsResponse = sendSms(phone, code);
        log.info(sendSmsResponse.getMessage());
        AssertsUtil.isTrue(!"OK".equals(sendSmsResponse.getMessage()), "短信发送失败");
        stringObjectValueOperations.set(phone, String.valueOf(code));
        redisTemplate.expire(phone, 300, TimeUnit.SECONDS);
    }

    @Override
    public void checkCode(String phone, String code) {
        AssertsUtil.isTrue(StringUtils.isEmpty(code), "验证码不能为空");
        ValueOperations<String, String> stringObjectValueOperations = redisTemplate.opsForValue();
        String s = stringObjectValueOperations.get(phone);
        AssertsUtil.isTrue(StringUtils.isEmpty(s), "验证码错误，请重试");
    }

    private Integer getCode() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }

    private SendSmsResponse sendSms(String phone, Integer code) {
        SendSmsRequest request = new SendSmsRequest();
        // Required:the mobile number
        request.setPhoneNumbers(phone);
        // Required:SMS-SignName-could be found in sms console
        request.setSignName(sign);
        // Required:Template-could be found in sms console
        request.setTemplateCode(templateCode);
        // Required:The param of sms template.For exmaple, if the template is "Hello,your verification code is ${code}". The param should be like following value
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = ismsService.sendSmsRequest(request);
        } catch (ClientException e) {
            log.error(e.getErrMsg());
            sendSmsResponse = new SendSmsResponse();
            sendSmsResponse.setMessage("发送短消息失败");
        }
        return sendSmsResponse;
    }

}
