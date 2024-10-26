package com.demo.user_service.Notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;

    // this is for sending simple plain text message to user
    public String sendMail(String to,String subject,String body){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(subject);
            message.setTo(to);
            message.setText(body);
            mailSender.send(message);
            return "Message Sent Successfully";
        }catch(Exception ex){
         throw new RuntimeException(ex.getMessage());
        }
    }

    public String SendTemplateMail(String to,String header,String body) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        try{
            helper.setTo(to);
            helper.setSubject(header);
            helper.setText(body,true);
            javaMailSender.send(msg);
            return "Mail send Successfully";
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
