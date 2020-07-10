package com.yangfan.v17order.config;

import com.yangfan.v17order.interceptor.AuthInterception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
//@Configuration
public class CartWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterception authInterception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterception).addPathPatterns("/**");
    }
}
