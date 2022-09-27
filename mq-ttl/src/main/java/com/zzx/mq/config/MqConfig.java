package com.zzx.mq.config;


import com.zzx.mq.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
    private static final Logger log = LoggerFactory.getLogger(MqConfig.class);

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


    /**
     * 使用插件来实现延迟交换机
     * @param student
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "delayQueue",durable = "true"),
            exchange = @Exchange(value = "delayExchange",durable = "true",delayed = "true"),
            key = {"delay"}
    ))
    public void listenerDelay(Student student){
        log.info("收到消息:{}",student);
    }

}