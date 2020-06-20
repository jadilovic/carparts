package com.avlija.parts;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration 
public class MailConfig {

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp-relay.sendinblue.com");
        javaMailSender.setPort(587);

        javaMailSender.setJavaMailProperties(getMailProperties());
        
        Authenticator auth = new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication("j.adilovic@gmail.com", "U1wAzvhVg8WXZrHS");
        	}
		};
		
		Session session = Session.getDefaultInstance(getMailProperties(), auth);
		
		javaMailSender.setSession(session);

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.debug", "false");
        
        return properties;
    }
}