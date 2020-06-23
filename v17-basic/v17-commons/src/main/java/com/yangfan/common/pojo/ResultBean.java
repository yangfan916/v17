package com.yangfan.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yangfan
 * @version 1.0
 * @description 统一定义了返回json的接口描述类型
 */
@Data
@AllArgsConstructor
public class ResultBean<T> {

    private String statusCode;
    private T data;
}
