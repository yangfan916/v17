package com.yangfan.v17searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfan.api.ISearchService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TProduct;
import com.yangfan.mapper.TProductMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean synAllData() {
        //1. 查询源数据
        List<TProduct> productList = productMapper.list();
        //2. Mysql-->solr
        for (TProduct product : productList) {
            //product--> document
            //1. 创建document对象
            SolrInputDocument document = new SolrInputDocument();
            //2. 设置相关的属性值
            document.setField("id", product.getId());
            document.setField("product_name", product.getName());
            document.setField("product_price", product.getPrice());
            document.setField("sale_point", product.getSalePoint());
            document.setField("product_images", product.getImages());
            //3. 保存
            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("500", "同步数据失败");
            }
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500", "同步数据失败");
        }
        return new ResultBean("200", "同步数据成功");
    }

    @Override
    public ResultBean synById(Long id) {
        TProduct product = productMapper.selectByPrimaryKey(id);
        //1. 创建document对象
        SolrInputDocument document = new SolrInputDocument();
        //2. 设置相关的属性值
        document.setField("id", product.getId());
        document.setField("product_name", product.getName());
        document.setField("product_price", product.getPrice());
        document.setField("sale_point", product.getSalePoint());
        document.setField("product_images", product.getImages());
        //3. 保存
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500", "同步数据失败");
        }
        return new ResultBean("200", "同步数据成功");
    }

    @Override
    public ResultBean deleteById(Long id) {
        try {
            solrClient.deleteById(String.valueOf(id));
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500", "同步数据失败");
        }

        return new ResultBean("200", "同步数据成功");
    }

    @Override
    public ResultBean queryByKeywords(String keywords) {
        return null;
    }
}
