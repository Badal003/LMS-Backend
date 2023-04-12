package com.example.tmpproject.controllers;

import com.example.tmpproject.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EmailController
{
    @Autowired
    private EmailService emailService;

    public String sendmail(String to,String subject,String text)
    {
        emailService.sendMail(to, subject, text);
        return "emailsent";
    }
}
