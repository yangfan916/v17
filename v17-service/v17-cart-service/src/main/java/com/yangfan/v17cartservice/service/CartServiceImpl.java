package com.yangfan.v17cartservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfan.api.ICartService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.pojo.CartItem;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Service
public class CartServiceImpl implements ICartService {

    @Resource(name = "myStringRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public ResultBean add(String token, Long productId, Integer count) {

        //1.根据token查询购物车信息
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        //entries(hkey),获取变量中的键值对
        Map<Object, Object> cart = redisTemplate.opsForHash().entries(key.toString());
        //2.购物车中存在已有商品
        if(cart != null){
            if(redisTemplate.opsForHash().hasKey(key.toString(), productId)){
                //如果操作商品以在购物车，直接修改数量
                CartItem cartItem = (CartItem) redisTemplate.opsForHash().get(key.toString(), productId);
                cartItem.setCount(cartItem.getCount() + count);
                cartItem.setUpdateTime(new Date());

                redisTemplate.opsForHash().put(key.toString(), productId, cartItem);
                redisTemplate.expire(key.toString(), 15, TimeUnit.DAYS);

                return new ResultBean("200", true);
            }
        }
        //添加商品到购物车
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setCount(count);
        cartItem.setUpdateTime(new Date());

        redisTemplate.opsForHash().put(key.toString(), productId, cartItem);
        redisTemplate.expire(key.toString(), 15, TimeUnit.DAYS);

        return new ResultBean("200", true);

    }

    @Override
    public ResultBean del(String token, Long productId) {
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        Long delete = redisTemplate.opsForHash().delete(key.toString(), productId);
        if( delete == 1){
            return new ResultBean("200", true);
        }
        return new ResultBean("404", false);
    }

    @Override
    public ResultBean update(String token, Long productId, Integer count) {
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        //查询记录是否存在
        CartItem cartItem = (CartItem) redisTemplate.opsForHash().get(key.toString(), productId);
        if(cartItem != null){
            cartItem.setCount(count);
            cartItem.setUpdateTime(new Date());
            redisTemplate.opsForHash().put(key.toString(), productId, cartItem);
            return new ResultBean("200", true);
        }
        return new ResultBean("404", false);

    }

    @Override
    public ResultBean query(String token) {

        StringBuilder key = new StringBuilder("user:cart:").append(token);
        //entries(hkey),获取变量中的键值对
        Map<Object, Object> cart = redisTemplate.opsForHash().entries(key.toString());
        if(cart != null){
            //1.获取到values并没有按照时间排序，通过TreeSet集合来按照更新时间排序
            Collection<Object> values = cart.values();
            TreeSet<CartItem> treeSet = new TreeSet<>();
            for(Object value : values){
                CartItem cartItem = (CartItem) value;
                treeSet.add(cartItem);
            }
            List<CartItem> result = new ArrayList<>(treeSet);
            return new ResultBean("200", result);
        }
        return new ResultBean("404", null);
    }
}
