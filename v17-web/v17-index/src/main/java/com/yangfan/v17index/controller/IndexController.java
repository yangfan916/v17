package com.yangfan.v17index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.IProductTypeService;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("show")
    public String showIndex(Model model){
        List<TProductType> list = productTypeService.list();
        model.addAttribute("list", list);
        return "index";
    }

    @RequestMapping("listType")
    @ResponseBody
    public ResultBean<TProductType> listType(){
        List<TProductType> list = productTypeService.list();
        return new ResultBean("200", list);
    }
}
