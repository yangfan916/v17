package com.yangfan.v17springbootemail;

import com.yangfan.v17springbootemail.service.MailService;
import com.yangfan.v17springbootemail.serviceimpl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17SpringbootEmailApplicationTests {

    private static final Logger logger  = LoggerFactory.getLogger(V17SpringbootEmailApplicationTests.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailServiceImpl mailService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendSimpleMailTest(){
        //1.构建邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yangfan163e@163.com");
        message.setTo("1024337691@qq.com");
        message.setSubject("springboot-javamail");
        message.setText("springboot整合javamail发送邮件");

        //2.发送邮件
        javaMailSender.send(message);
    }

    @Test
    public void sendHTMLMailTest(){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("yangfan163e@163.com");
            helper.setTo("1024337691@qq.com");
            helper.setSubject("springboot-javamail");
            helper.setText("springboot整合javamail发送邮件（支持HTML标签）,<a href='https://www.cnblogs.com/Post-90sDachenchen/p/11015289.html'>示例参考</a>", true);

            //2.发送邮件
            javaMailSender.send(mimeMessage);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e.getMessage());
        }
    }

    @Test
    public void sendAttachmentsMailTest(){
        String path = "D:\\softwore\\JDK环境搭建.txt";
        mailService.sendAttachmentsMail("1024337691@qq.com", "springboot-javamail", "有附件测试报告，请查收！",path);
    }

    @Test
    public void sendTemplateMailTest(){
        mailService.sendTemplateMail("1024337691@qq.com", "springboot-javamail-生日祝福", "杨帆");
    }

    @Test
    public void rabbitEmailTest(){
        mailService.rabbitEmail();
    }

}
