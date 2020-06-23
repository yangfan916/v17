package com.yangfan.common.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public abstract class IBaseServiceImp<T> implements IBaseService<T> {

    public abstract IBaseDao<T> getBaseDao();

    public int deleteByPrimaryKey(Long id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    public int insert(T t) {
        return getBaseDao().insert(t);
    }

    public int insertSelective(T t) {
        return getBaseDao().insertSelective(t);
    }

    public T selectByPrimaryKey(Long id) {
        return getBaseDao().selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T t) {
        return getBaseDao().updateByPrimaryKeySelective(t);
    }

    public int updateByPrimaryKeyWithBLOBs(T t) {
        return getBaseDao().updateByPrimaryKeyWithBLOBs(t);
    }

    public int updateByPrimaryKey(T t) {
        return getBaseDao().updateByPrimaryKey(t);
    }

    public List<T> list() {
        return getBaseDao().list();
    }

    public PageInfo<T> page(Integer pageIndex, Integer pageSize) {
        //1. 设置分页信息
        PageHelper.startPage(pageIndex, pageSize);
        //2. 获取到信息集合
        List<T> list = this.list();
        //3. 返回分页对象
        PageInfo<T> pageInfo = new PageInfo<T>(list, 3);
        return pageInfo;
    }
}
