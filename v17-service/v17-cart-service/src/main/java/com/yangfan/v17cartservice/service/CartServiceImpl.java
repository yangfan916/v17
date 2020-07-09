package com.yangfan.v17cartservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfan.api.ICartService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.pojo.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
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
        if(cart.size() != 0){
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
        if(cart.size() > 0){
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

    @Override
    public ResultBean merge(String nologinKey, String loginKey) {
        //目标：将未登录购物车合并到已登录购物车
        log.info("将未登录购物车合并到已登录购物车");
        //1.判断未登录购物车是否存在
        Map<Object, Object> nologinCart = redisTemplate.opsForHash().entries(nologinKey);
        if(nologinCart.size() == 0){
            return new ResultBean("200", "无需合并");
        }
        //2.判断已登录购物车是否存在
        Map<Object, Object> loginCart = redisTemplate.opsForHash().entries(loginKey);
        if(loginCart.size() == 0){
            //将未登录购物车变成已登录购物车
            redisTemplate.opsForHash().putAll(loginKey, nologinCart);
            //删除未登录的购物车
            redisTemplate.delete(nologinKey);

            return new ResultBean("200", "合并成功");
        }

        //3.未登录 已登录购物车均存在
        //相同的商品数量叠加
        //不同的商品直接添加
        //以已登录购物车为准遍历未登录购物车，看有没有相同的商品
        Set<Map.Entry<Object, Object>> nologinEntries = nologinCart.entrySet();
        for (Map.Entry<Object, Object> nologinEntry : nologinEntries) {
            //nologinEntry.getKey() //productId
            //nologinEntry.getValue() //cartItem
            CartItem cartItem = (CartItem) redisTemplate.opsForHash().get(loginKey, nologinEntry.getKey());
            if(cartItem != null){
                //存在，则做数量叠加
                CartItem nologinCartItem = (CartItem) nologinEntry.getValue();
                cartItem.setCount(cartItem.getCount() + nologinCartItem.getCount());
                cartItem.setUpdateTime(new Date());
                redisTemplate.opsForHash().put(loginKey, nologinEntry.getKey(), cartItem);
            }else{
                //不存在，直接添加商品
                redisTemplate.opsForHash().put(loginKey, nologinEntry.getKey(), nologinEntry.getValue());
            }
        }
        //删除未登录的购物车
        redisTemplate.delete(nologinKey);
        return new ResultBean("200", "合并成功");
    }
}
