package com.example.hrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String sendingEmail, String subject, String data) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("arm19980201@yandex.com");
        message.setTo(sendingEmail);
        message.setSubject(subject);
        message.setText(data);
        javaMailSender.send(message);
    }
}
