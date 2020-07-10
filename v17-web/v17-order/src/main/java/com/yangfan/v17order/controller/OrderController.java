package com.yangfan.v17order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangfan
 * @version 1.0
 * @description
 * 1.进入订单确认页
 * 1.1 当前省份合法验证
 * 1.2 展示相关数据
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @RequestMapping("toConfirm")
    public String toConfirm(){

        System.out.println("去订单确认页面");

        //1.获取到当前用户
        //2.获取当前用户对应的购物车信息
        //3.获取当前用户的收件人信息
        //4.获取付款方式
        //5.获取物流商信息

        return "confirm.html";
    }

}
