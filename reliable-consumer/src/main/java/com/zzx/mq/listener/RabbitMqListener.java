package com.zzx.mq.listener;

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
 * @date 2022:09:26 17:09:57
 */

@Component
public class RabbitMqListener {

    private static final Logger log= LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("reliableQueue"),
            exchange = @Exchange(value = "reliableExchange",type = ExchangeTypes.DIRECT),
            key = {"reliable"}
    ))
    public void reliable01(String msg){
        log.info("收到消息:{}",msg);
    }
}
