package com.chintec.message.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.common.util.AssertsUtil;
import com.chintec.message.entity.MessageRec;
import com.chintec.message.service.ISmsServices;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 10:51
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class MessageMqListener {
    @Autowired
    private ISmsServices iSmsServices;

    @RabbitListener(queues = "message")
    @RabbitHandler
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        AssertsUtil.isTrue(StringUtils.isEmpty(message), "发送的信息不能为空");
        MessageRec messageRec = JSONObject.parseObject(message.getBody(), MessageRec.class);
        try {
            switch (messageRec.getType()) {
                case 1:
                    iSmsServices.sendSms(messageRec.getMessage());
                    break;
                case 0:
                    break;
                default:
                    AssertsUtil.isTrue(true, "无此消息类型");
                    break;
            }
            // 手动签收消息,通知mq服务器端删除该消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Object o = headers.get(AmqpHeaders.DELIVERY_TAG);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            System.out.println(o);
            System.out.println(deliveryTag);
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
