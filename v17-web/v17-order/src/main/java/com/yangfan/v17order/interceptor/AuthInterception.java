package com.yangfan.v17order.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.IUserService;
import com.yangfan.common.pojo.ResultBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Component
public class AuthInterception implements HandlerInterceptor {

    @Reference
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前是否处于登录状态
        //如果是登录状态，则将当前用户信息保存到request中
        //登录了，才可以放行
        Cookie[] cookies = request.getCookies();
        if( cookies != null ){
            for (Cookie cookie : cookies){
                if("user_token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    ResultBean resultBean = userService.checkIsLogin(token);
                    if("200".equals(resultBean.getStatusCode())){
                        request.setAttribute("user", resultBean.getData());
                        return true;
                    }
                }
            }
        }
        //未登录不放行，跳转到登录页面
        //如果需要实现从哪来回哪去的效果，那么此处需要传递一个url地址
        response.sendRedirect("http://sso.yf.com:9095?referer=" + request.getRequestURL().toString());
        return false;
    }
}
