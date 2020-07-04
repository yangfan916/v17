package com.yangfan.v17springbootemail.service;

import org.springframework.stereotype.Service;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public interface MailService {

    /**
     * 简单邮件发送
     * @param to
     * @param subject
     * @param content
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * html邮件发送
     * @param to
     * @param subject
     * @param content
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 带附件的邮件发送
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 持模板化的方式
     * @param to
     * @param subject
     * @param username
     */
    void sendTemplateMail(String to, String subject,String username);


}
