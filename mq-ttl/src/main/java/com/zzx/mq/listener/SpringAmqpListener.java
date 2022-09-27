package com.zzx.mq.listener;

import com.zzx.mq.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
    public Exchange simpleExchange(){
        return new DirectExchange("simpleExchange",true,false);
    }

    // 声明普通的queue队列，并且为其指定死信交换机，设置队列的消息最长时间为10S
    @Bean
    public Queue simpleQueue(){
        return QueueBuilder
                .durable("simpleQueue")
                .ttl(10000)
                .deadLetterExchange("deadExchange")
                .deadLetterRoutingKey("dead")
                .build();
    }

    // 绑定普通交换机和队列
    @Bean
    public Binding commonBind(){
        return BindingBuilder.bind(simpleQueue()).to(simpleExchange()).with("simple").noargs();
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


    // 刻意不去监听普通交换机的消息，等待消息的TTL到期，则就会进入死信队列
    // @RabbitListener(queues = {"simpleQueue"})
    // public void listenerCommon(Student student){
    // log.info("收到消息:{}",student);
    // }

    // 监听死信交换机的消息
    @RabbitListener(queues = {"deadQueue"})
    public void listenerDead(Student student){
        log.info("死信交换机绑定的队列收到消息:{}",student);
    }


}
