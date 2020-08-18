package com.chintec.message.controller;

import com.chintec.message.service.ISmsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/18 11:21
 */
@RestController
@RequestMapping("/sms/v1")
public class SmsController {
    @Autowired
    private ISmsServices iSmsServices;

    @GetMapping("sms")
    public void sendSms(String phone) {
        iSmsServices.sendSms(phone);
    }
}
