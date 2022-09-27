package com.zzx.mq.listener;

import com.zzx.mq.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:27 09:55:30
 */

@Component
public class SpringAmqpListener {

    private static final Logger log = LoggerFactory.getLogger(SpringAmqpListener.class);

    // 声明普通的交换机
    @Bean
    public Exchange commonExchange(){
        return new DirectExchange("commonExchange",true,false);
    }

    // 声明普通的queue队列，并且为其指定死信交换机
    @Bean
    public Queue commonQueue(){
        return QueueBuilder
                .durable("commonQueue")
                .deadLetterExchange("deadExchange")
                .deadLetterRoutingKey("dead")
                .build();
    }

    // 绑定普通交换机和队列
    @Bean
    public Binding commonBind(){
        return BindingBuilder.bind(commonQueue()).to(commonExchange()).with("common").noargs();
    }




    // 声明死信交换机
    @Bean
    public Exchange deadExchange(){
        return new DirectExchange("deadExchange",true,false);
    }

    // 声明存储死信的队列 dead.queue
    @Bean
    public Queue deadQueue(){
        return new Queue("deadQueue", true);
    }

    // 将死信队列 与 死信交换机绑定
    @Bean
    public Binding deadBind(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead").noargs();
    }


    // 监听普通交换机的消息
    @RabbitListener(queues = {"commonQueue"})
    public void listenerCommon(Student student){
        log.info("模拟异常，观察死信交换机是否收到");
        int i=1/0;
        log.info("收到消息:{}",student);
    }

    // 监听死信交换机的消息
    @RabbitListener(queues = {"deadQueue"})
    public void listenerDead(Student student){
        log.info("死信交换机绑定的队列收到消息:{}",student);
    }

}
