package com.zzx.mq;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:26 17:36:20
 */

@SpringBootTest
public class TestMq {
    private static final Logger log= LoggerFactory.getLogger(TestMq.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testConfirm() throws InterruptedException {
        String message="正在测试provider-confirm";

        //定义数据对象，传入消息的ID
        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());

        //添加ConfirmCallback
        correlationData.getFuture().addCallback(
                (success) -> {
                    if(success.isAck()){
                        //表明到达了队列,返回ACK
                        log.info("消息成功到交换机, 消息的ID是:{}", correlationData.getId());
                    }else {
                        //表明没到达交换机，返回NACK
                        log.error("消息未到达交换机, 消息ID:{}, 原因{}",correlationData.getId(), success.getReason());
                    }
                },
                (ex) -> {
                    log.error("消息发送过程出现未知异常, ID:{}, 原因{}",correlationData.getId(),ex.getMessage());
                }
        );

        //发送消息
        rabbitTemplate.convertAndSend("reliableExchange", "reliable", message, correlationData);

        //休眠一会儿，等待ack回执
        Thread.sleep(2000);
    }
}
