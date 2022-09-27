package com.zzx.mq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.ImmediateRequeueMessageRecoverer;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:26 17:10:24
 */

@Configuration
public class MqConfig implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(MqConfig.class);

    /**
     * MQ的消息转换器
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


//    @Bean
//    public MessageRecoverer messageRecoverer(){
//        //重试结束后立即删除【默认类型】
//        return new RejectAndDontRequeueRecoverer();
//    }

//    @Bean
//    public MessageRecoverer messageRecoverer(){
//        //重试结束后立即重新入队，相当于本地重试几次之后又入队，然后继续本地重试，循环往复..
//        //只是速度没有SpringAMQP那么快，但是也会造成不小压力
//        return new ImmediateRequeueMessageRecoverer();
//    }

    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate){
        //重试结束后还是不成功，则消息直接进入新的交换机【消息--->交换机】
        return new RepublishMessageRecoverer(rabbitTemplate,"errorExchange","error");
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取RabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 设置ReturnCallback
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 投递失败，记录日志
            log.info("消息发送到交换机成功，到队列失败，应答码{}，原因{}，交换机{}，路由键{},消息{}",
                    replyCode, replyText, exchange, routingKey, message.toString());
            // 如果有业务需要，可以重发消息
        });
    }
}