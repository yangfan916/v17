package com.yangfan.v17smsservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yangfan
 */
@SpringBootApplication
@EnableDubbo
public class V17SmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V17SmsServiceApplication.class, args);
    }

}
