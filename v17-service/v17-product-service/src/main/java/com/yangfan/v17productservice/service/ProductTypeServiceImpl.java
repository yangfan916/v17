package com.yangfan.v17productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfan.api.IProductTypeService;
import com.yangfan.common.base.IBaseDao;
import com.yangfan.common.base.IBaseServiceImp;
import com.yangfan.entity.TProductType;
import com.yangfan.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Service
@Component
public class ProductTypeServiceImpl extends IBaseServiceImp<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;


    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return productTypeMapper;
    }
}
