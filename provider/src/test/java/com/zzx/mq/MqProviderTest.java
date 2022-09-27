package com.zzx.mq;

import com.zzx.mq.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:26 11:33:08
 */

@SpringBootTest
public class MqProviderTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testFanout(){
        String message="你好，我在使用SpringAMQP";
        String exchange="fanoutExchange";

        rabbitTemplate.convertAndSend(exchange,"",message);
    }

    @Test
    public void testDirect(){
        String exchange="directExchange";
        String key1="red";
        String key2="yellow";
        String key3="green";
        rabbitTemplate.convertAndSend(exchange,key1,"red");
        rabbitTemplate.convertAndSend(exchange,key2,"yellow");
        rabbitTemplate.convertAndSend(exchange,key3,"green");
    }

    @Test
    public void testTopic(){
        String exchange="topicExchange";
        //都会被收到
        String key1="china.news";

        //china.#收到
        String key2="china.sichuan.news";

        //都会被收到
        String key3="japan.news";

        //japan.#收到
        String key4="japan.dongjing.news";

        rabbitTemplate.convertAndSend(exchange,key1,"中国.消息");
        rabbitTemplate.convertAndSend(exchange,key2,"中国.四川.消息");
        rabbitTemplate.convertAndSend(exchange,key3,"日本.消息");
        rabbitTemplate.convertAndSend(exchange,key4,"日本.东京.消息");
    }


    @Test
    public void testSendObject(){
        Student student = new Student("周志雄",21,'男');
        String exchange="objectExchange";
        String key="object";
        rabbitTemplate.convertAndSend(exchange,key,student);
    }

}
