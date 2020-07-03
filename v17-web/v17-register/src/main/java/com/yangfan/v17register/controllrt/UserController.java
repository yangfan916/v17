package com.yangfan.v17register.controllrt;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.IUserService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Reference
    private IUserService userService;

    @GetMapping("checkUsernameIsExists/{username}")
    @ResponseBody
    public ResultBean checkUsernameIsExists(@PathVariable("username") String username){
        return userService.checkUsernameIsExists(username);
    }

    @GetMapping("checkPhoneIsExists/{phone}")
    @ResponseBody
    public ResultBean checkPhoneIsExists(@PathVariable("phone") String phone){
        return userService.checkPhoneIsExists(phone);
    }

    @GetMapping("checkEmailIsExists/{email}")
    @ResponseBody
    public ResultBean checkEmailIsExists(@PathVariable("email") String email){
        return userService.checkEmailIsExists(email);
    }

    /**
     * 生成验证码接口
     * @param identification
     * @return
     */
    @PostMapping("generateCode/{identification}")
    @ResponseBody
    public ResultBean generateCode(@PathVariable("identification") String identification ){

        return userService.generateCode(identification);
    }

    /**
     * 处理异步请求
     * @param user
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ResultBean register(TUser user){
        return null;
    }

    /**
     * 处理同步请求，跳转到相关页面
     * @param user
     * @return
     */
    @PostMapping("register4PC")
    public String register4PC(TUser user){
        return null;
    }

    @GetMapping("activating")
    public String activating(String token){
        return null;
    }
}
