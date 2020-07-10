package com.yangfan.v17order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlipayBizContent {

    //订单编号
    private String out_trade_no;
    //平台和支付宝签约的商品类型，比如快捷支付，严格来说不需要提供set方法
    private String product_code = "FAST_INSTANT_TRADE_PAY";
    //订单总金额
    private String total_amount;
    private String subject;
    private String body;

}
