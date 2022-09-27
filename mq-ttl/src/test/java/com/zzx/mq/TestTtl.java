package com.zzx.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzx.mq.domain.Student;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:27 11:05:29
 */

@SpringBootTest
public class TestTtl {

    private static final Logger log = LoggerFactory.getLogger(TestTtl.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test01() throws InterruptedException, JsonProcessingException {
        Student student = new Student();
        student.setAge(20);
        student.setName("张三");
        student.setAddress("四川.成都");

        //先转为json字符串
        ObjectMapper objectMapper=new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(student);

        //定义消息，消息持久化
        Message message = MessageBuilder
                .withBody(bytes)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();

        //定义消息ID
        CorrelationData data=new CorrelationData(UUID.randomUUID().toString());

        //定义collback
        data.getFuture().addCallback(
                (success)->{
                    if(success.isAck()){
                        log.info("到达交换机，ID是{}",data.getId());
                    }else {
                        log.info("未到达交换机，ID是{}",data.getId(),"原因:{}",success.getReason());
                    }
                },
                (ex)->{
                    log.info("消息发送出现未知异常，ID是{}",data.getId(),"原因:{}",ex.getMessage());
                }
        );

        //发送消息
        rabbitTemplate.convertAndSend(
                "simpleExchange","simple",message,data);

        Thread.sleep(2000);
    }


    @Test
    public void test02() throws InterruptedException, JsonProcessingException {
        Student student = new Student();
        student.setAge(20);
        student.setName("周志雄");
        student.setAddress("四川.成都");

        //先转为json字符串
        ObjectMapper objectMapper=new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(student);

        //定义消息，消息持久化，发送消息的时候指定消息的TTL，当队列和消息都存在TTL，则使用消息的TTL
        Message message = MessageBuilder
                .withBody(bytes)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setExpiration("5000")
                .build();

        //定义消息ID
        CorrelationData data=new CorrelationData(UUID.randomUUID().toString());

        //定义collback
        data.getFuture().addCallback(
                (success)->{
                    if(success.isAck()){
                        log.info("到达交换机，ID是{}",data.getId());
                    }else {
                        log.info("未到达交换机，ID是{}",data.getId(),"原因:{}",success.getReason());
                    }
                },
                (ex)->{
                    log.info("消息发送出现未知异常，ID是{}",data.getId(),"原因:{}",ex.getMessage());
                }
        );

        //发送消息
        rabbitTemplate.convertAndSend(
                "simpleExchange","simple",message,data);

        Thread.sleep(2000);
    }



    @Test
    public void test03() throws InterruptedException, JsonProcessingException {
        Student student = new Student();
        student.setAge(20);
        student.setName("周志雄");
        student.setAddress("四川.成都");

        //先转为json字符串
        ObjectMapper objectMapper=new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(student);


        //使用插件来实现延迟消息，发送消息时，一定要携带x-delay属性，指定延迟的时间：
        Message message = MessageBuilder
                .withBody(bytes)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setHeader("x-delay",10000)
                .setExpiration("5000")
                .build();

        //定义消息ID
        CorrelationData data=new CorrelationData(UUID.randomUUID().toString());

        //定义collback
        data.getFuture().addCallback(
                (success)->{
                    if(success.isAck()){
                        log.info("到达交换机，ID是{}",data.getId());
                    }else {
                        log.info("未到达交换机，ID是{}",data.getId(),"原因:{}",success.getReason());
                    }
                },
                (ex)->{
                    log.info("消息发送出现未知异常，ID是{}",data.getId(),"原因:{}",ex.getMessage());
                }
        );

        //发送消息
        rabbitTemplate.convertAndSend(
                "delayExchange","delay",message,data);

        Thread.sleep(2000);
    }
}