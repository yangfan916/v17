package com.yangfan.v17item.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Configuration
public class CommonConfig {

    @Bean
    public ThreadPoolExecutor intiThreadPoolExecutor(){
        //获取当前服务器的CUP核数
        int processors = Runtime.getRuntime().availableProcessors();
        //创建自定义的线程池
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                processors, processors * 2, 1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100));

        return poolExecutor;
    }
}
