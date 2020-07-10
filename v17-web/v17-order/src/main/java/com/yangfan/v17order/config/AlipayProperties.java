package com.yangfan.v17order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayProperties {

    private String serverUrl;
    private String appId;
    private String priviteKey;
    private String format;
    private String charset;
    private String alipayPulicKey;
    private String signType;

}
