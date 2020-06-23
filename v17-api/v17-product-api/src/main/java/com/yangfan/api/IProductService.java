package com.yangfan.api;

import com.github.pagehelper.PageInfo;
import com.yangfan.api.vo.ProductVO;
import com.yangfan.common.base.IBaseService;
import com.yangfan.entity.TProduct;

public interface IProductService extends IBaseService<TProduct> {

    Long add(ProductVO productVO);

}
