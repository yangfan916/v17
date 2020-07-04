package com.yangfan.v17springbootemail.serviceimpl;

import com.yangfan.v17springbootemail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger  = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        //1.构建邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        //2.发送邮件
        javaMailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            //2.发送邮件
            javaMailSender.send(mimeMessage);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e.getMessage());
        }
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            //helper.setCc(from);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource fileSystemResource = new FileSystemResource(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);

            logger.info("文件名：" + fileName);
            helper.addAttachment(fileName, fileSystemResource);
            javaMailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }

    @Override
    public void sendTemplateMail(String to, String subject, String username) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);

            //邮件内容由模板来产生
            Context context = new Context();
            context.setVariable("username", username);
            String content = templateEngine.process("brithday", context);

            helper.setText(content, true);

            javaMailSender.send(message);
            logger.info("模板化的方式发送邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void rabbitEmail(){
        Map<String,String> map = new HashMap<>();
        map.put("to", "1024337691@qq.com");
        map.put("username", "赵敏");
        rabbitTemplate.convertAndSend("email-exchange","email.birthday", map);
    }
}
