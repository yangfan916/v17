package com.yangfan.v17productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yangfan.api.IProductService;
import com.yangfan.api.vo.ProductVO;
import com.yangfan.common.base.IBaseDao;
import com.yangfan.common.base.IBaseServiceImp;
import com.yangfan.entity.TProduct;
import com.yangfan.entity.TProductDesc;
import com.yangfan.mapper.TProductDescMapper;
import com.yangfan.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
public class ProductServiceImpl extends IBaseServiceImp<TProduct> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return productMapper;
    }

    @Override
    @Transactional
    public Long add(ProductVO productVO) {

        productMapper.insertSelective(productVO.getProduct());

        TProductDesc productDesc = new TProductDesc();
        productDesc.setProductId(productVO.getProduct().getId());
        productDesc.setProductDesc(productVO.getProductDesc());

        productDescMapper.insert(productDesc);

        return productVO.getProduct().getId();
    }
}
