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
     * 交换机，队列持久化
     * @param student
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "durableQueue",durable = "true"),
            exchange = @Exchange(value = "durableExchange",type = ExchangeTypes.DIRECT,durable = "true"),
            key = {"durable"}
    ))
    public void listener01(Student student){
        log.info("收到消息:{}",student);
    }

}
