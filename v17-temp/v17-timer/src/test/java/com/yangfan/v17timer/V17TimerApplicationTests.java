package com.yangfan.v17timer;

import com.yangfan.v17timer.timerTask.MyTimerTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Timer;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class V17TimerApplicationTests {

    @Test
    public void contextLoads() {
    }

    /**
     * JDK默认提供的定时任务实现-Timer
     * 底层机制：后台只开辟一个线程去服务，JDK自带
     * @param args
     */
    public static void main(String[] args) {
        MyTimerTask myTimerTask = new MyTimerTask();
        Timer timer = new Timer();
        //启动后等待10s开始执行，1s执行1次
        //参数2：delay，什么时候开始
        //参数3：间隔多上时间执行1次
        timer.schedule(myTimerTask, 10000, 1000);
    }

}
