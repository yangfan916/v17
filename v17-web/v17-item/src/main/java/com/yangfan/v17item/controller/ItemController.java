package com.yangfan.v17item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.IProductService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TProduct;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
@Controller
@RequestMapping("item")
public class ItemController {

    @Reference
    private IProductService productService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @RequestMapping("createById/{id}")
    @ResponseBody
    public ResultBean createById(@PathVariable("id") Long id) throws IOException, TemplateException {

        //1.根据 id 获取商品数据
        TProduct product = productService.selectByPrimaryKey(id);
        //2.采用Freemarker生成对应的商品详情页
        Template template = configuration.getTemplate("item.ftl");
        Map<String, Object> data = new HashMap<>();
        data.put("product", product);
        //3.输出
        //获取到项目运行时的路径
        //获取 static 的路径
        String serverpath = ResourceUtils.getURL("classpath:static").getPath();
        log.info("运行时路径：" + serverpath);
        StringBuilder path = new StringBuilder(serverpath).append(File.separator).append(id).append(".html");
        log.info("生成的html页面的路径：" + path);
        FileWriter out = new FileWriter(path.toString());
        template.process(data, out);

        return new ResultBean("200", "生成静态页面成功");
    }

    public ResultBean batchCreate(@RequestParam List<Long> ids){

        //16核，8G内存
        //多线程 16*2
        //线程池
        //如何让创建线程
        List<Future<Long>> results = new ArrayList<>(ids.size());
        for(Long id : ids){
            //调用方法--》创建一个线程来执行任务
            //串行--》并行
            //create(id);
            results.add(poolExecutor.submit(new CreateHTMLTask(id)));
        }

        //后续的结果处理
        List<Long> errors = new ArrayList<>();
        for (Future<Long> future : results) {
            try {
                //获取执行结果，阻塞等待，只有前边并行的多线程执行完后，这里才开始执行
                Long result = future.get();
                if( result != 0 ){
                    errors.add(result);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        for (Long error : errors) {
            log.info("错误的记录ids = " + errors.toString());
            //发生错误之后的处理过程：
            //1.将处理错误的id信息保存到日志表中
            //id  product_id  retry_times create_time  update_time

            //2.通过定时任务扫描这张表
            //select * from t_create_html_log where retry_times <= 3
            //update retry_times + 1

            //3.超过3此的记录需要人工介入
        }

        return new ResultBean("200", "批量生成页面成功");
    }

    private class CreateHTMLTask implements Callable<Long>{

        private Long id;

        public CreateHTMLTask(Long id){
            this.id = id;
        }

        /**
         * 执行成功返回0
         * 执行失败，返回当前的记录id
         * @return
         * @throws Exception
         */
        @Override
        public Long call() {

            //1.根据 id 获取商品数据
            TProduct product = productService.selectByPrimaryKey(id);
            //2.采用Freemarker生成对应的商品详情页
            Template template = null;
            try {
                template = configuration.getTemplate("item.ftl");
                Map<String, Object> data = new HashMap<>();
                data.put("product", product);
                //3.输出
                //获取到项目运行时的路径
                //获取 static 的路径
                String serverpath = ResourceUtils.getURL("classpath:static").getPath();
                log.info("运行时路径：" + serverpath);
                StringBuilder path = new StringBuilder(serverpath).append(File.separator).append(id).append(".html");
                log.info("生成的html页面的路径：" + path);
                FileWriter out = new FileWriter(path.toString());
                template.process(data, out);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
                return id;
            }

            return 0L;
        }
    }

    public static void main(String[] args) {
        //查看CPU核数
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(processors);
        //processors = 8 ， 8核
    }

}
