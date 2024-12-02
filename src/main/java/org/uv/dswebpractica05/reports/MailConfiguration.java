/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.reports;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author juan
 */
@Configuration
public class MailConfiguration {
    
    @Value("${email.sender}")
    private String emailUser;
    
    @Value("${email.password}")
    private String emailPassword;
    
    @Bean
    public JavaMailSender javaMailSender(){
        
        
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        
        emailSender.setHost("smtp.gmail.com");
        emailSender.setPort(587);
        emailSender.setUsername(emailUser);
        emailSender.setPassword(emailPassword);
        
        Properties props = emailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        
        return emailSender;
    }
}
