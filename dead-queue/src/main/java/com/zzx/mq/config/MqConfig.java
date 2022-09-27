package com.zzx.mq.config;


import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:26 17:10:24
 */

@Configuration
public class MqConfig{
    /**
     * MQ的消息转换器
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageRecoverer messageRecoverer(){
        //重试结束后立即删除【默认类型】
        return new RejectAndDontRequeueRecoverer();
    }

}