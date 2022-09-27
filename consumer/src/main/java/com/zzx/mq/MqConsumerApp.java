package com.zzx.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:26 11:32:34
 */

@SpringBootApplication
public class MqConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(MqConsumerApp.class,args);
    }
}
