package com.yangfan.v17productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfan.api.IProductTypeService;
import com.yangfan.common.base.IBaseDao;
import com.yangfan.common.base.IBaseServiceImp;
import com.yangfan.entity.TProductType;
import com.yangfan.mapper.TProductTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
@Service
@Component
public class ProductTypeServiceImpl extends IBaseServiceImp<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Resource(name = "myStringRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return productTypeMapper;
    }

    /**
     * 重写后去列表的方法，加入缓存的处理逻辑
     * @return
     */
    @Override
    public List<TProductType> list() {

        log.info("商品列表查询，查redis缓存");

        //1.查询当前缓存是否存在分类信息
        List<TProductType> list = (List<TProductType>) redisTemplate.opsForValue().get("productType:list");
        if (list == null || list.size() == 0 ){
            log.info("商品列表查询，查数据库");
            //2.缓存不存在，则查询数据库
            list = super.list();
            //3.将查询结果保存在缓存中
            redisTemplate.opsForValue().set("productType:list", list);
        }

        return list;
    }
}
