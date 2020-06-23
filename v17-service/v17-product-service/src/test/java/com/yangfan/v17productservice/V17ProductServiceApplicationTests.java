package com.yangfan.v17productservice;

import com.github.pagehelper.PageInfo;
import com.yangfan.api.vo.ProductVO;
import com.yangfan.entity.TProduct;
import com.yangfan.v17productservice.service.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17ProductServiceApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(V17ProductServiceApplicationTests.class);

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getById(){
        TProduct product = productServiceImpl.selectByPrimaryKey(1L);
        log.info("商品名：" + product.getName());
    }

    @Test
    public void list(){
        List<TProduct> list = productServiceImpl.list();
        for(TProduct pro : list){
            log.info("商品名：" + pro.getName());
        }
        Assert.assertNotNull(list);
    }

    @Test
    public void page(){
        PageInfo<TProduct> page = productServiceImpl.page(2, 2);
        log.info("大小 = " + page.getList().size());
        Assert.assertEquals(2,page.getList().size());
    }

    @Test
    public void add(){
        ProductVO vo = new ProductVO();
        TProduct pro = new TProduct();
        pro.setImages("123");
        pro.setName("锤子手机");
        pro.setPrice(1699L);
        pro.setSalePoint("老罗独家创作技术");
        pro.setSalePrice(1599L);
        pro.setTypeId(1);
        pro.setTypeName("电子数码");
        vo.setProduct(pro);
        vo.setProductDesc("怎么都摔不坏的手机");
        log.info("插入的主键值 = " + productServiceImpl.add(vo));
    }
}
