package com.chintec.backofiice.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import reactor.util.annotation.NonNullApi;

import java.util.UUID;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 11:07
 */
@Configuration
@Slf4j
public class RabbitMqConfig {
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
        return BindingBuilder.bind(queue()).to(topicExchange()).with("topic.msg");
    }

}
