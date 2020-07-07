package com.yangfan.pojo;

import com.yangfan.entity.TProduct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Data
@AllArgsConstructor
public class CartItemVO implements Serializable {

    private TProduct product;

    private Integer count;

    private Date updateTime;
}
