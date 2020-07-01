package com.yangfan.common.constant;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public interface MQConstant {

    /**
     * 通过内部类更好的分类管理
     */

    public static class EXCHANGE{
        public static final String CENTER_PRODUCT_EXCHANGE = "center-product-exchange";
    }

    public static class QUEUE{
        public static final String CENTER_PRODUCT_EXCHANGE_SEARCH_QUEUE = "center-product-exchange-search-queue";
    }

}
