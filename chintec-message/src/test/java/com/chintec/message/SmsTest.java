package com.chintec.message;

import com.chintec.message.service.ISmsServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/18 13:22
 */
@SpringBootTest
class SmsTest {
    @Autowired
    private ISmsServices iSmsServices;

    @Test
    void sendSms() {
        iSmsServices.sendSms("15737911393");
    }
}
