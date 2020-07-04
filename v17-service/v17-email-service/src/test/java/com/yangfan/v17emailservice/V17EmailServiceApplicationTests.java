package com.yangfan.v17emailservice;

import com.yangfan.api.IEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17EmailServiceApplicationTests {

    @Autowired
    private IEmailService emailService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendBirthdayMailTest(){
        emailService.sendBirthdayMail("1024337691@qq.com", "杨帆");
    }

}
