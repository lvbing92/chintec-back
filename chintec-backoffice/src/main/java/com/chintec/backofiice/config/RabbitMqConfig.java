package com.chintec.backofiice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 11:07
 */
@Configuration
public class RabbitMqConfig {

    @Autowired
    RabbitAdmin rabbitAdmin;

    /**
     * 申明队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("message");
    }

    /**
     * 申明交换机（主题模式）
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 将队列绑定到交换机
     *
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("topic.with");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public void createExchangeQueue() {
        rabbitAdmin.declareExchange(topicExchange());
        rabbitAdmin.declareQueue(queue());
    }

}
