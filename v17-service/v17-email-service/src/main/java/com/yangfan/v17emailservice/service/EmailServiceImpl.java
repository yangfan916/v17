package com.yangfan.v17emailservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfan.api.IEmailService;
import com.yangfan.common.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${email.server}")
    private String from;

    @Override
    public ResultBean sendBirthdayMail(String to, String username) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setCc(from);
            helper.setTo(to);
            helper.setSubject("springboot-javamail-生日祝福");

            //邮件内容由模板来产生
            Context context = new Context();
            context.setVariable("username", username);
            String content = templateEngine.process("brithday", context);

            helper.setText(content, true);

            javaMailSender.send(message);
            return new ResultBean("200", "生日祝福的邮件发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResultBean("500","生日祝福的邮件发送失败");
    }

    @Override
    public ResultBean sendActiviteMail(String to, String username) {
        return null;
    }
}
