package com.yangfan.v17userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sun.xml.internal.ws.encoding.HasEncoding;
import com.yangfan.api.IUserService;
import com.yangfan.common.base.IBaseDao;
import com.yangfan.common.base.IBaseServiceImp;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.common.utils.CodeUtils;
import com.yangfan.entity.TUser;
import com.yangfan.mapper.TUserMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Service
public class UserServiceImp extends IBaseServiceImp<TUser> implements IUserService {

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "myStringRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResultBean checkUsernameIsExists(String username) {
        return null;
    }

    @Override
    public ResultBean checkPhoneIsExists(String phone) {
        return null;
    }

    @Override
    public ResultBean checkEmailIsExists(String email) {
        return null;
    }

    @Override
    public ResultBean generateCode(String identification) {
        //1.生成验证码
        String code = CodeUtils.generateCode(6);
        //2.往redis保存一个凭证和验证码的对应关系key-value
        redisTemplate.opsForValue().set(identification, code, 2, TimeUnit.MINUTES);
        //3.给手机发送验证码
        //3.1.调通阿里云提供的短信demo
        //3.2.发送短信的功能，整个体系很多系统都会用上，变成一个公共的服务v17-sms-service
        //3.3.发送消息，异步处理发送短信
        Map<String,String> map = new HashMap<>();
        map.put("identification", identification);
        map.put("code", code);
        rabbitTemplate.convertAndSend("sms-exchange","sms-code", map);

        return new ResultBean("200", "ok");
    }

    @Override
    public ResultBean checkLogin(TUser user) {
        //1.根据用户输入的账号（手机/邮箱）信息去查询
         TUser userdb = userMapper.selectByIdentification(user.getUsername());
         //2.根据查询出来的密码信息进行比较
         if (userdb != null){
             if (user.getPassword().equals(userdb.getPassword())){
                 //往redis保存凭证信息,并设置过期时间为30分钟
                 String uuid = UUID.randomUUID().toString();
                 redisTemplate.opsForValue().set("user:token:" + uuid, userdb.getUsername(), 30, TimeUnit.MINUTES);
                 //return new ResultBean("200", userdb.getUsername());
                 return new ResultBean("200", uuid);
             }
         }
        return new ResultBean("404", null);
    }

    @Override
    public ResultBean checkIsLogin(String userTokenUUID) {
        String username = (String) redisTemplate.opsForValue().get(userTokenUUID);
        if (username != null){
            //刷新凭证的有效期
            redisTemplate.expire(userTokenUUID, 30, TimeUnit.MINUTES);
            return new ResultBean("200", username);
        }
        return new ResultBean("404", null);
    }

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return null;
    }
}
