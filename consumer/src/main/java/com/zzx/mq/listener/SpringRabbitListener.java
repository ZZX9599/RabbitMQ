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
 * @date 2022:09:26 11:59:42
 */

@Component
public class SpringRabbitListener {
    private static final Logger log = LoggerFactory.getLogger(SpringRabbitListener.class);


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue1"),
            exchange = @Exchange(value = "fanoutExchange",type = ExchangeTypes.FANOUT)
    ))
    public void fanoutConsumer1(String msg){
        log.info("fanout消费者一收到消息:{}",msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue2"),
            exchange = @Exchange(value = "fanoutExchange",type = ExchangeTypes.FANOUT)
    ))
    public void fanoutConsumer2(String msg){
        log.info("fanout消费者二收到消息:{}",msg);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue1"),
            exchange = @Exchange(value = "directExchange",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}
    ))
    public void directConsumer1(String msg){
        log.info("direct消费者一收到消息:{}",msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue2"),
            exchange = @Exchange(value = "directExchange",type = ExchangeTypes.DIRECT),
            key = {"red","green"}
    ))
    public void directConsumer2(String msg){
        log.info("direct消费者二收到消息:{}",msg);
    }


    /**
     * 监听china开头的所有消息，japan开头的一个词的消息
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue1"),
            exchange = @Exchange(value = "topicExchange",type = ExchangeTypes.TOPIC),
            key = {"china.#","japan.*"}
    ))
    public void topicConsumer1(String msg){
        log.info("topic消费者一收到消息:{}",msg);
    }

    /**
     * 监听china开头的一个词的消息，japan开头的所有消息
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue2"),
            exchange = @Exchange(value = "topicExchange",type = ExchangeTypes.TOPIC),
            key = {"china.*","japan.#"}
    ))
    public void topicConsumer2(String msg){
        log.info("topic消费者二收到消息:{}",msg);
    }


    /**
     * 测试MQ的消息转换器
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "objectQueue"),
            exchange = @Exchange(value = "objectExchange",type = ExchangeTypes.TOPIC),
            key = {"object"}
    ))
    public void objectConsumer(String msg){
        log.info("消费者收到消息:{}",msg);
    }
}
