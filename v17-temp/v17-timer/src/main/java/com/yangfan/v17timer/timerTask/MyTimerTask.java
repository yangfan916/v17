package com.yangfan.v17timer.timerTask;

import java.util.Date;
import java.util.TimerTask;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public class MyTimerTask extends TimerTask {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": " +new Date());
    }
}
