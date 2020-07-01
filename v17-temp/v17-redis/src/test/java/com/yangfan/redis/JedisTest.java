package com.yangfan.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public class JedisTest {

    @Test
    public void stringTest(){
        Jedis jedis = new Jedis("192.168.149.128", 6379);
        jedis.auth("0916");
        jedis.set("target", "JedisTest");
        String target = jedis.get("target");
        System.out.println(target);

        for( int i = 0; i < 10; i++){
            jedis.incr("zan");   //自增
        }
        System.out.println(jedis.get("zan"));
    }

    @Test
    public void otherTest(){
        Jedis jedis = new Jedis("192.168.149.128", 6379);
        jedis.auth("0916");

        jedis.lpush("list", "a","b","c","d");
        List<String> list = jedis.lrange("list", 0, -1);
        System.out.println(list.toString());

        Long sadd = jedis.sadd("set", "a", "a", "b", "c");
        System.out.println(sadd);

        Map<String, Double> map = new HashMap<String, Double>();
        map.put("java", 100.0);
        map.put("php", 101.0);
        jedis.zadd("hotbook", map);

    }
}
