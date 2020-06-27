package com.yangfan.v17search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.ISearchService;
import com.yangfan.common.pojo.PageResultBean;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("synAllData")
    @ResponseBody
    public ResultBean synAllData(){
        return searchService.synAllData();
    }

    @RequestMapping("queryByKeyWords")
    @ResponseBody
    public ResultBean queryByKeyWords(String keyWords){
        return searchService.queryByKeywords(keyWords);
    }

    @RequestMapping("queryByKeywordsPC/{pageIndex}/{pageSize}")
    public String queryByKeywordsPC(String keywords, Model model,
                                    @PathVariable("pageIndex") Integer pageIndex,
                                    @PathVariable("pageSize") Integer pageSize){
        ResultBean resultBean = searchService.queryByKeywords(keywords, pageIndex, pageSize);
        if( "200".equals(resultBean.getStatusCode()) ){
            //List<TProduct> list = (List<TProduct>) resultBean.getData();
            PageResultBean<TProduct> pageResultBean = (PageResultBean<TProduct>) resultBean.getData();
            model.addAttribute("page", pageResultBean);
        }
        return "list";
    }
}
