package com.yangfan.api;

import com.yangfan.common.pojo.ResultBean;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public interface IEmailService {

    /**
     * 发送邮件的接口（模板方式）
     * @param to 接收人的邮箱地址
     * @param username 接收人用户名
     */
    ResultBean sendBirthdayMail(String to, String username);

    /**
     * 激活使用的接口
     * @param to
     * @param username
     */
    ResultBean sendActiviteMail(String to, String username);
}
