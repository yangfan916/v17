package com.yangfan.v17sso;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yangfan
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
public class V17SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(V17SsoApplication.class, args);
    }

}