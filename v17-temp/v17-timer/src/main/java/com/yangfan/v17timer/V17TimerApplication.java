package com.yangfan.v17timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yangfan
 */
@SpringBootApplication
@EnableScheduling
public class V17TimerApplication {

    public static void main(String[] args) {
        SpringApplication.run(V17TimerApplication.class, args);
    }

}
