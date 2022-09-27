package com.zzx.mq.listener;

import com.zzx.mq.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:27 09:55:30
 */

@Component
public class SpringAmqpListener {

    private static final Logger log = LoggerFactory.getLogger(SpringAmqpListener.class);

    /**
     * 消费者确认机制
     * @param student
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "consumerConfirmQueue",durable = "true"),
            exchange = @Exchange(value = "consumerConfirmExchange",type = ExchangeTypes.DIRECT,durable = "true"),
            key = {"consumerConfirm"}
    ))
    public void listener01(Student student){
        log.info("模拟出现异常，看MQ消息还在不在");
        int i=10/0;
        log.info("收到消息:{}",student);
    }


    /**
     * 本地重试失败的消息
     * @param student
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "errorQueue",durable = "true"),
            exchange = @Exchange(value = "errorExchange",type = ExchangeTypes.DIRECT,durable = "true"),
            key = {"error"}
    ))
    public void error(Student student){
        log.info("本地重试失败的消息=====>{}",student);
    }


}
