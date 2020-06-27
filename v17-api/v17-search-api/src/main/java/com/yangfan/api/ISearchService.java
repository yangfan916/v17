package com.yangfan.api;

import com.yangfan.common.pojo.ResultBean;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public interface ISearchService {

    /**
     * 做全量同步
     * 在数据初始化的时候使用
     * @return
     */
    public ResultBean synAllData();

    /**
     * 增量同步
     * @param id
     * @return
     */
    public ResultBean synById(Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    public ResultBean deleteById(Long id);

    /**
     * 查询操作
     * @param keywords
     * @return
     */
    public ResultBean queryByKeywords(String keywords);

    /**
     * 搜索：分页查询
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ResultBean queryByKeywords(String keywords, Integer pageIndex, Integer pageSize);
}
