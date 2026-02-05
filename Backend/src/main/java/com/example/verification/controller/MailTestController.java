package com.example.verification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailTestController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/test-mail")
    public String sendTestMail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("YOUR_EMAIL@gmail.com"); // test recipient
            message.setSubject("Test Mail");
            message.setText("Hello! This is a test email from Spring Boot using app password.");
            mailSender.send(message);
            return "Mail Sent Successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
