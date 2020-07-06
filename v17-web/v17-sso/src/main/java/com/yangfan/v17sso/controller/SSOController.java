package com.yangfan.v17sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.IUserService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
@Controller
@RequestMapping("sso")
public class SSOController {

    @Reference
    private IUserService userService;

    @PostMapping("checkLogin")
    @ResponseBody
    public ResultBean checkLogin(TUser user){
        return userService.checkLogin(user);
    }

    @PostMapping("checkLogin4PC")
    public String checkLogin4PC(TUser user, HttpServletRequest request, HttpServletResponse response){
        ResultBean resultBean = userService.checkLogin(user);
        if ("200".equals(resultBean.getStatusCode())) {

            //写cookie给客户端，保存凭证
            //1.获取uuid
            String uuid = (String) resultBean.getData();
            log.info("====jwtToken = " + uuid);
            //2.创建cookie对象
            Cookie cookie = new Cookie("user_token", uuid);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            //3.写cookie到客户端
            response.addCookie(cookie);

            //request.getSession().setAttribute("user", user.getUsername());
            return "redirect:http://localhost:9091";
        }
        return "index";
    }

    @GetMapping("logout")
    @ResponseBody
    public ResultBean logout(@CookieValue(name = "user_token", required = false) String token,
                             HttpServletResponse response){
        //request.getSession().removeAttribute("user");

        if(token != null){
            //创建cookie对象
            Cookie cookie = new Cookie("user_token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            //将cookie清除，设置有效期为0
            cookie.setMaxAge(0);
            //3.写cookie到客户端
            response.addCookie(cookie);
        }
        return new ResultBean("200", true);
    }

    @GetMapping("logout4PC")
    public String logout4PC(){
        return null;
    }

    /*@GetMapping("checkIsLogin")
    @ResponseBody
    public ResultBean checkIsLogin(HttpServletRequest request){

        //1.从cookie中获取user_token的值
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0){
            //遍历查询需要的那个cookie
            for (Cookie cookie : cookies){
                if("user_token".equals(cookie.getName())){
                    String uuid = cookie.getValue();
                    //2.去redis中查询是否存在该凭证信息
                    String userTokenUUID = "user:token:" + uuid;
                    ResultBean resultBean = userService.checkIsLogin(userTokenUUID);
                    return resultBean;
                }
            }
        }

        return new ResultBean("404", null);
    }*/

    @GetMapping("checkIsLogin")
    @CrossOrigin(origins = "*",allowCredentials = "true") //允许跨域访问携带cookie,默认false,开启为true
    @ResponseBody
    public ResultBean checkIsLogin(@CookieValue(name = "user_token", required = false) String uuid){

        //1.从cookie中获取user_token的值
        if(uuid != null){
            //2.去redis中查询是否存在该凭证信息
//            String userTokenUUID = "user:token:" + uuid;
//            ResultBean resultBean = userService.checkIsLogin(userTokenUUID);

            ResultBean resultBean = userService.checkIsLogin(uuid);

            return resultBean;
        }

        return new ResultBean("404", null);
    }

    @GetMapping("checkIsLogin4PC")
    public String checkIsLogin4PC(){
        return null;
    }

}
