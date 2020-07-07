package com.yangfan.v17cartservice;

import com.yangfan.api.ICartService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.pojo.CartItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17CartServiceApplicationTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void addTest(){
        ResultBean resultBean = cartService.add("123", 3L, 100);
        System.out.println(resultBean.getStatusCode() + "," + resultBean.getData());
    }

    @Test
    public void queryTest(){
        ResultBean query = cartService.query("123");
        List<CartItem> cartItems = (List<CartItem>) query.getData();
        for (CartItem cartItem : cartItems) {
            System.out.println(cartItem);
        }
    }

    @Test
    public void updateTest(){
        ResultBean update = cartService.update("123", 5l, 500);
        System.out.println(update.getData());
    }

    @Test
    public void delTest(){
        ResultBean del = cartService.del("123", 1L);
        System.out.println(del.getStatusCode());
    }

}
