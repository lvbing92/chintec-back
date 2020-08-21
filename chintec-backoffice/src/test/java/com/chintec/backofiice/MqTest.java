package com.chintec.backofiice;

import com.chintec.backofiice.mq.MqSendMessage;
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
    private MqSendMessage mqSendMessage;

    @Test
    void setMqSendUtil() {
        mqSendMessage.send("18206116926");
    }
}
