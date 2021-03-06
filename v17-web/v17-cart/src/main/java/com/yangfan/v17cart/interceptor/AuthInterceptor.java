package com.yangfan.v17cart.interceptor;

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
public class AuthInterceptor implements HandlerInterceptor {

    @Reference
    private IUserService userService;

    /**
     * 前置拦截器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前是否处于登录状态
        //如果是登录状态，则将当前用户信息保存到request中
        //不管是否已登录，都放行
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
        return true;
    }
}
