package com.yangfan.api;

import com.yangfan.common.pojo.ResultBean;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public interface ICartService {

    /**
     * 添加商品到购物车
     * @param token      购物车的标识
     * @param productId  购买的商品id
     * @param count      购买的商品数量
     * @return
     */
    ResultBean add(String token, Long productId, Integer count);

    /**
     * 从购物设删除商品
     * @param token
     * @param productId
     * @return
     */
    ResultBean del(String token, Long productId);

    /**
     * 更新购物车的商品
     * @param token
     * @param productId
     * @param count
     * @return
     */
    ResultBean update(String token, Long productId, Integer count);

    /**
     * 查询购物车的商品
     * @param token
     * @return
     */
    ResultBean query(String token);

    /**
     * 登录之后购物车合并
     * @param nologinKey
     * @param loginKey
     * @return
     */
    ResultBean merge(String nologinKey, String loginKey);
}
