package com.yangfan.v17timer.timerTask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Component
public class MySpringTask {

    /**
     * 注意，SpringBoot提供的定时任务，默认也是单线程的执行模式
     */

    //fixedRate隔多久执行一次
    //@Scheduled(fixedRate = 2000)
    @Scheduled(cron = "0/5 * * * * ?")
    public void run(){
        System.out.println(Thread.currentThread().getName() + "-->" + new Date() + " run1");
    }

    //@Scheduled(cron = "0/3 * * * * ?")
    public void run2(){
        System.out.println(Thread.currentThread().getName() + "-->" + new Date() + " run2");
    }
}
