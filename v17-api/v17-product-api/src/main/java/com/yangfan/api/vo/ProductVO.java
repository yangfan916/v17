package com.yangfan.api.vo;

import com.yangfan.entity.TProduct;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Data
public class ProductVO implements Serializable {

    private TProduct product;
    private String productDesc;
}
