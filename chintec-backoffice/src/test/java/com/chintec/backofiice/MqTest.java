package com.chintec.backofiice;

import com.chintec.backofiice.util.MqSendUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 11:20
 */
@SpringBootTest
public class MqTest {
    @Autowired
    private MqSendUtil mqSendUtil;

    @Test
    void setMqSendUtil() {
        mqSendUtil.send();
    }
}
