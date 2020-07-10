package com.yangfan.v17order.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Configuration
public class AlipayConfig {

    @Autowired
    private AlipayProperties alipayProperties;

    @Bean
    public AlipayClient getAlipayClient(){
        AlipayClient alipayClient =  new DefaultAlipayClient(
                alipayProperties.getServerUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getPriviteKey(),
                alipayProperties.getFormat(),
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPulicKey(),
                alipayProperties.getSignType()
        );
        return alipayClient;
    }
}
