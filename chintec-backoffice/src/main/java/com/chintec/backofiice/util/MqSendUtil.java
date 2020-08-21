package com.chintec.backofiice.util;

import com.alibaba.fastjson.JSONObject;
import com.chintec.backofiice.entity.MessageRec;
import com.rabbitmq.client.ConfirmCallback;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 11:12
 */
@Component

public class MqSendUtil implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        MessageRec messageRec = new MessageRec();
        messageRec.setType(1);
        messageRec.setMessage("18206116926");
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.convertAndSend("topicExchange", "topic.msg", JSONObject.toJSONString(messageRec));
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("ack:" + ack);
        if (!ack) {
            System.out.println(cause);
        }
    }
}