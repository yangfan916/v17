package com.yangfan.v17item.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Data
@AllArgsConstructor
public class Product {

    private String name;
    private Long price;
    private Date createTime;
}
