package com.yangfan.common.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 抽取共性的方法，普通的 crud
 */
public interface IBaseService<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKeyWithBLOBs(T t);

    int updateByPrimaryKey(T t);

    List<T> list();

    PageInfo<T> page(Integer pageIndex, Integer pageSize);
}
