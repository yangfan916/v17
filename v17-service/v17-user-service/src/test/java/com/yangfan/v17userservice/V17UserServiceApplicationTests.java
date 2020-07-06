package com.yangfan.v17userservice;

import com.yangfan.api.IUserService;
import com.yangfan.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17UserServiceApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void checkLogin(){
        TUser user = new TUser();
        user.setUsername("12345678901");
        user.setPassword("123456");
        userService.checkLogin(user);
    }
}
