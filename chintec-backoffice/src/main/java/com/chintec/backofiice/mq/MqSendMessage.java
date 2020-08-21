package com.chintec.backofiice.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.backofiice.entity.MessageRec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 11:12
 */
@Slf4j
@Component
public class MqSendMessage {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String phone) {
        MessageRec messageRec = new MessageRec();
        messageRec.setMessage(phone);
        messageRec.setType(1);
        this.rabbitTemplate.setMandatory(true);
        /*
        确认回调机制当 ack为false的时候会再次向mq发送消息
         */
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("correlationData:" + correlationData);
            log.info("ack:" + ack);
            if (!ack) {
                log.info(cause);
            }
        });
        /*
        returnCallBack 当前队列无效或是被解绑的时候执行里面操作
         */
        this.rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info(Arrays.toString(message.getBody()));
        });
        CorrelationData correlationData = new CorrelationData(String.valueOf(UUID.randomUUID()));
        rabbitTemplate.convertAndSend("topicExchange", "topic.with", JSONObject.toJSONString(messageRec), correlationData);
    }
}