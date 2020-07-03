package com.yangfan.sms.api;

import com.yangfan.sms.pojo.SMSResponse;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public interface ISMS {

    /**
     * 发送验证码的接口服务
     * @param phone
     * @param code
     * @return
     */
    public SMSResponse sendCodeMessage(String phone, String code);
}
