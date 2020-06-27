package com.yangfan.v17searchservice;

import com.yangfan.api.ISearchService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TProduct;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17SearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ISearchService searchService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void addOrUpdateTest() throws IOException, SolrServerException {
        //1. 创建document对象
        SolrInputDocument document = new SolrInputDocument();
        //2. 设置相关的属性值
        document.setField("id", 250);
        document.setField("product_name", "华为P40Pro250");
        document.setField("product_price", 6488);
        document.setField("sale_point", "麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦");
        document.setField("product_images", "123");
        //3. 保存
//        solrClient.add(document);
//        solrClient.commit();
        solrClient.add("collection2", document);
        solrClient.commit("collection2");
    }

    @Test
    public void testQuery() throws IOException, SolrServerException {
        //1. 组装查询对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("product_name:华为P40");
        //2. 执行查询
        QueryResponse queryResponse = solrClient.query(solrQuery);
        //3. 得到结果
        SolrDocumentList results = queryResponse.getResults();
        for (SolrDocument result : results) {
            System.out.println(result.getFieldValue("product_name") + ": " + result.getFirstValue("product_price"));
        }
    }

    @Test
    public void delTest() throws IOException, SolrServerException {
        //solrClient.deleteByQuery("product:华为P40");
        solrClient.deleteById("1");
        solrClient.commit();
    }

    @Test
    public void synAllDataTest(){
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getData());
    }

    @Test
    public void synById(){
        ResultBean resultBean = searchService.synById(11L);
        System.out.println(resultBean.getData());
    }

    @Test
    public void queryByKeywords(){
        ResultBean resultBean = searchService.queryByKeywords("小米");
        List<TProduct> products = (List<TProduct>) resultBean.getData();
        for(TProduct product : products){
            System.out.println(product.getName());
        }
    }
}
