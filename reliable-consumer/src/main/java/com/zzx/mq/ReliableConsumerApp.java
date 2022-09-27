package com.zzx.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:26 17:00:31
 */

@SpringBootApplication
public class ReliableConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ReliableConsumerApp.class,args);
    }
}
