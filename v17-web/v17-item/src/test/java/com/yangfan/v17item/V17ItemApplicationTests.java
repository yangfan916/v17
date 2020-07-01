package com.yangfan.v17item;

import com.yangfan.v17item.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class V17ItemApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(V17ItemApplicationTests.class);

    @Autowired
    private Configuration configuration;

    @Test
    public void contextLoads() {
    }

    @Test
    public void createHTMLTest() throws IOException, TemplateException {

        //1.获取到模板数据
        Template template = configuration.getTemplate("freemarker.ftl");
        //2.组装数据
        Map<String,Object> data = new HashMap<>();
        data.put("username", "freemarker");

        //保存对象
        Product product = new Product("12345", 9999l, new Date());
        data.put("product", product);

        List<Product> list = new ArrayList<>();
        list.add(new Product("华为", 5000L, new Date()));
        list.add(new Product("小米", 4000L, new Date()));
        data.put("list", list);

        data.put("age", 27);
        //3.模板+数据结合 输出
        FileWriter fileWriter = new FileWriter("D:\\IdeaProjects\\v17\\v17-web\\v17-item\\src\\main\\resources\\static\\f.html");
        template.process(data, fileWriter);
        log.info("生成静态页面成功");
    }

}
