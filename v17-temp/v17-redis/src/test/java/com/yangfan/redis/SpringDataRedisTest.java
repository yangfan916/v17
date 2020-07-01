package com.yangfan.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void stringTest(){
        redisTemplate.opsForValue().set("smallTarget", "先赚1个亿吧");
        Object smallTarget = redisTemplate.opsForValue().get("smallTarget");
        System.out.println(smallTarget);
    }

    @Test
    public void hashTest(){
        redisTemplate.opsForHash().put("book", "name", "钢铁是怎样炼成的");
        Object o = redisTemplate.opsForHash().get("book", "name");
        System.out.println(o);
    }

    @Test
    public void numberIncrTest(){
        //必须要先对值进行序列化，否则无法进行自增运算的
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set("money", "1000");
        redisTemplate.opsForValue().increment("money",1000);
        Object money = redisTemplate.opsForValue().get("money");
        System.out.println(money);

        /**
         * 大部分情况下，key序列化方式为String，value序列化方式为JDK
         * 当我们需要进行数学运算时，value的序列化方式要设置成String
         */
    }

    @Test
    public void runTest(){
        long start = System.currentTimeMillis();

        //批处理，使用流水线，减少了网络传输，更快

        redisTemplate.executePipelined(new SessionCallback<Object>() {
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                for(int i = 0; i < 100000; i++){
                    redisTemplate.opsForValue().set("k"+i,"v"+i);
                }
                return null;
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start); //12278   549
    }

    @Test
    public void ttlTest(){
        redisTemplate.opsForValue().set("18192328396", "123321");
        redisTemplate.expire("18192328396", 2, TimeUnit.MINUTES);
        System.out.println(redisTemplate.getExpire("18192328396"));
    }
}
