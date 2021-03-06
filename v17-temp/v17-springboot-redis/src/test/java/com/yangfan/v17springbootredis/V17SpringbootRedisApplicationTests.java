package com.yangfan.v17springbootredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17SpringbootRedisApplicationTests {

    @Resource(name = "redisTemplate1")
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        Object k1 = redisTemplate.opsForValue().get("k1");
        System.out.println(k1);
    }

}
