package com.yangfan.v17searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfan.api.ISearchService;
import com.yangfan.common.pojo.PageResultBean;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TProduct;
import com.yangfan.mapper.TProductMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        //TODO 只查询需要的数据
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

        //1. 组装查询查询条件
        SolrQuery solrQuery = new SolrQuery();
        if (keywords == null || "".equals(keywords.trim())){
            solrQuery.setQuery("product_name:华为");
        }else{
            solrQuery.setQuery("product_name:" + keywords);
        }

        //ADD1 添加高亮的设置
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        //2. 获取结果，并将结果转换为list
        List<TProduct> list = null;
        try {
            QueryResponse response = solrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();
            list = new ArrayList<>(results.size());

            //ADD2. 获取高亮的信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            for (SolrDocument document : results) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                //product.setName(document.getFieldValue("product_name").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                product.setImages(document.getFieldValue("product_images").toString());
                product.setSalePoint(document.getFieldValue("sale_point").toString());
                //TODO 传递 product 对象不合适，因为页面展示只需要4个字段

                //设置product_name的高亮信息
                //1.获取当前记录的高亮信息
                Map<String, List<String>> highlight = highlighting.get(document.getFieldValue("id").toString());
                //2.获取字段对应的高亮信息
                List<String> productNameHiglight = highlight.get("product_name");
                //3.判断
                if(productNameHiglight != null && productNameHiglight.size() > 0){
                    //高亮信息
                    product.setName(productNameHiglight.get(0));
                }else{
                    //普通文本信息
                    product.setName(document.getFieldValue("product_name").toString());
                }


                list.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500", null);
        }

        return new ResultBean("200", list);
    }

    @Override
    public ResultBean queryByKeywords(String keywords, Integer pageIndex, Integer pageSize) {
        //1. 组装查询查询条件
        SolrQuery solrQuery = new SolrQuery();
        if (keywords == null || "".equals(keywords.trim())){
            solrQuery.setQuery("product_name:华为");
        }else{
            solrQuery.setQuery("product_name:" + keywords);
        }

        //ADD1 添加高亮的设置
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        //添加分页的设置
        //从哪个下标开始
        solrQuery.setStart((pageIndex-1)*pageSize);
        solrQuery.setRows(pageSize);

        //2. 获取结果，并将结果转换为list
        List<TProduct> list = null;

        int total = 0;

        PageResultBean<TProduct> pageResultBean = new PageResultBean<>();

        try {
            QueryResponse response = solrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();
            list = new ArrayList<>(results.size());
            total = (int) results.getNumFound();
            //ADD2. 获取高亮的信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            for (SolrDocument document : results) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                //product.setName(document.getFieldValue("product_name").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                product.setImages(document.getFieldValue("product_images").toString());
                product.setSalePoint(document.getFieldValue("sale_point").toString());
                //TODO 传递 product 对象不合适，因为页面展示只需要4个字段

                //设置product_name的高亮信息
                //1.获取当前记录的高亮信息
                Map<String, List<String>> highlight = highlighting.get(document.getFieldValue("id").toString());
                //2.获取字段对应的高亮信息
                List<String> productNameHiglight = highlight.get("product_name");
                //3.判断
                if(productNameHiglight != null && productNameHiglight.size() > 0){
                    //高亮信息
                    product.setName(productNameHiglight.get(0));
                }else{
                    //普通文本信息
                    product.setName(document.getFieldValue("product_name").toString());
                }


                list.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500", null);
        }

        pageResultBean.setList(list);
        pageResultBean.setPageNum(pageIndex);
        pageResultBean.setPageSize(pageSize);
        pageResultBean.setNavigatePages(3);
        pageResultBean.setTotla(total);
        pageResultBean.setPages(total%pageSize == 0 ? total/pageSize : total/pageSize + 1);


        return new ResultBean("200", pageResultBean);
    }
}
