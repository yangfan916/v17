package com.yangfan.v17center.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yangfan.api.IProductService;
import com.yangfan.api.vo.ProductVO;
import com.yangfan.entity.TProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;

    @RequestMapping("get/{id}")
    @ResponseBody
    public TProduct getById(@PathVariable("id") Long id){
        return productService.selectByPrimaryKey(id);
    }

    @RequestMapping("list")
    public String productList(Model model){
        List<TProduct> productList = productService.list();
        model.addAttribute("list", productList);
        return "product/list";
    }

    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(Model model, @PathVariable("pageIndex") Integer pageIndex,@PathVariable("pageSize") Integer pageSize){
        PageInfo<TProduct> page = productService.page(pageIndex, pageSize);
        model.addAttribute("page", page);
        return "product/list";
    }

    @PostMapping("add")
    public String add(ProductVO productVO){
        Long newId = productService.add(productVO);
        return "redirect:/product/page/1/2";
    }
}
