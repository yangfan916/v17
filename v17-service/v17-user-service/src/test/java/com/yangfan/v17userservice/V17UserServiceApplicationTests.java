package com.yangfan.v17userservice;

import com.yangfan.api.IUserService;
import com.yangfan.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17UserServiceApplicationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 加密测试
     */
    @Test
    public void encodeTest(){

        //MD5加密是不可逆的
        //abc-->def 但根据def解密得不到abc，MD5在线解密，会有一个数据字典（原文-密文），根据数据字典去查找的
        String encode1 = passwordEncoder.encode("123456");
        String encode2 = passwordEncoder.encode("123456");
        System.out.println(encode1);
        System.out.println(encode2);

        //$2a$10$/0xA8iPWYdX96eVAPgd.NOawbqjvU6Ow47Z6YiJ4XKFtKJzx4GlbO
        //$2a$10$n/FNABgouLNv126Dp/Ej.usKMuf3eW/knIQDwvGapPs0P6kH5QNp.
    }

    @Test
    public void decodeTest(){
        System.out.println(passwordEncoder.matches(
                "123456", "$2a$10$/0xA8iPWYdX96eVAPgd.NOawbqjvU6Ow47Z6YiJ4XKFtKJzx4GlbO"));
        System.out.println(passwordEncoder.matches(
                "123456", "$2a$10$n/FNABgouLNv126Dp/Ej.usKMuf3eW/knIQDwvGapPs0P6kH5QNp."));
    }

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
