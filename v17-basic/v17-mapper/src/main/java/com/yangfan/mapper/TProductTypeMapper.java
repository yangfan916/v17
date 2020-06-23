package com.yangfan.mapper;

import com.yangfan.entity.TProductType;

public interface TProductTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TProductType record);

    int insertSelective(TProductType record);

    TProductType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TProductType record);

    int updateByPrimaryKey(TProductType record);
}