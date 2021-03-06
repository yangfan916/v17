package com.yangfan.v17searchservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yangfan
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.yangfan.mapper")
public class V17SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V17SearchServiceApplication.class, args);
    }

}
