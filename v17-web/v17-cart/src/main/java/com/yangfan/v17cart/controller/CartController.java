package com.yangfan.v17cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.ICartService;
import com.yangfan.api.IUserService;
import com.yangfan.common.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
@RequestMapping("cart")
@RestController
public class CartController {

    @Reference
    private ICartService cartService;

    @Reference
    private IUserService userService;

    @GetMapping("add/{productId}/{count}")
    public ResultBean add(@PathVariable("productId") Long productId, @PathVariable("count") Integer count,
                          @CookieValue(name = "cart_token", required = false) String cartToken,
                          HttpServletRequest request,
                          HttpServletResponse response){

        String userToken = (String) request.getAttribute("user");
        if(userToken != null){
            //说明已经登录
            return cartService.add(userToken, productId, count);
        }

        //未登录
        if(cartToken == null){
            cartToken = UUID.randomUUID().toString();
        }
        //写cookie到客户端，更新有效期
        reflushCookie(cartToken, response);

        return cartService.add(cartToken, productId, count);

        //如果是未登录，传递一个cartToken
        //已登陆，传递当前登录用户的唯一标识
        //具体如何让判断是否已登录，复用之前的服务

        //方式1：http://localhost:9095/sso/ckechIsLogin
        //HttpClient 发送http请求

        //方式2：调用service
    }

    @GetMapping("query")
    public ResultBean query(@CookieValue(name = "cart_token", required = false) String cartToken,
                            HttpServletRequest request,
                            HttpServletResponse response){

        String userToken = (String) request.getAttribute("user");
        if(userToken != null){
            //说明已经登录
            return cartService.query(userToken);
        }

        if(cartToken != null){
            ResultBean resultBean = cartService.query(cartToken);
            //写cookie到客户端，更新有效期
            reflushCookie(cartToken, response);
            return resultBean;
        }

        return new ResultBean("404", null);
    }

    @GetMapping("update/{productId}/{count}")
    public ResultBean update(@PathVariable("productId") Long productId,
                             @PathVariable("count") Integer count,
                             @CookieValue(name = "cart_token", required = false) String cartToken,
                             HttpServletRequest request,
                             HttpServletResponse response){

        String userToken = (String) request.getAttribute("user");
        if(userToken != null){
            //说明已经登录
            return cartService.update(userToken, productId, count);
        }

        if(cartToken != null){
            ResultBean resultBean = cartService.update(cartToken, productId, count);
            //写cookie到客户端，更新有效期
            reflushCookie(cartToken, response);
            return resultBean;
        }

        return new ResultBean("404", false);
    }

    @GetMapping("delete/{productId}")
    public ResultBean update(@PathVariable("productId") Long productId,
                             @CookieValue(name = "cart_token", required = false) String cartToken,
                             HttpServletRequest request,
                             HttpServletResponse response){

        String userToken = (String) request.getAttribute("user");
        if(userToken != null){
            //说明已经登录
            return cartService.del(userToken, productId);
        }

        if(cartToken != null){
            ResultBean resultBean = cartService.del(cartToken, productId);
            //写cookie到客户端，更新有效期
            reflushCookie(cartToken, response);
            return resultBean;
        }

        return new ResultBean("404", false);
    }

    private void reflushCookie(@CookieValue(name = "cart_token", required = false) String cartToken,
                               HttpServletResponse response){

        Cookie cookie = new Cookie("cart_token", cartToken);
        cookie.setPath("/");
        cookie.setDomain("yf.com");
        //单位是秒
        cookie.setMaxAge(60*60*24*15);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
