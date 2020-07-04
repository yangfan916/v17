package com.yangfan.v17timer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(getTaskExecutor());
    }

    @Bean
    public Executor getTaskExecutor(){
        //此处应该是采用自定义线程池更合适!
        return Executors.newScheduledThreadPool(100);
    }
}
