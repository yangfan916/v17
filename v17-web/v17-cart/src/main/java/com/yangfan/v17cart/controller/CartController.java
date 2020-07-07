package com.yangfan.v17cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.ICartService;
import com.yangfan.common.pojo.ResultBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@RequestMapping("cart")
@RestController
public class CartController {

    @Reference
    private ICartService cartService;

    @PostMapping("add/{productId}/{count}")
    public ResultBean add(@PathVariable("productId") Long productId, @PathVariable("count") Integer count,
                          @CookieValue(name = "cart_token", required = false) String cartToken,
                          HttpServletResponse response){

        if(cartToken == null){
            cartToken = UUID.randomUUID().toString();
        }
        //写cookie到客户端，更新有效期
        reflushCookie(cartToken, response);
        return cartService.add(cartToken, productId, count);
    }

    @GetMapping("query")
    public ResultBean query(@CookieValue(name = "cart_token", required = false) String cartToken,
                            HttpServletResponse response){

        if(cartToken != null){
            ResultBean resultBean = cartService.query(cartToken);
            //写cookie到客户端，更新有效期
            reflushCookie(cartToken, response);
            return resultBean;
        }

        return new ResultBean("404", null);
    }

    private void reflushCookie(@CookieValue(name = "cart_token", required = false) String cartToken,
                               HttpServletResponse response){

        Cookie cookie = new Cookie("cart_token", cartToken);
        cookie.setPath("/");
        //单位是秒
        cookie.setMaxAge(60*60*24*15);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
