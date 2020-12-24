package com.avlija.parts.service;
import java.util.Properties;

/*
 * NOT USED
 * USED ONLY FORE EXPERIMENTAL PURPOSES / NOT PART OF THE WORKING APPLICATION
 * ONLY EXPERIMENTAL
 */

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
 public static void main(String[] args) {
 // Add recipient
 String to = "adilovic79@yahoo.com";

// Add sender
 String from = "j.adilovic@gmail.com";
 final String username = "j.adilovic@gmail.com";//your Gmail username 
 final String password = "";//your Gmail password

String host = "smtp.gmail.com";

Properties props = new Properties();
 props.put("mail.smtp.auth", "true");
 props.put("mail.smtp.starttls.enable", "true"); 
 props.put("mail.smtp.host", host);
 props.put("mail.smtp.port", "587");

// Get the Session object
 Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	 protected PasswordAuthentication getPasswordAuthentication() {
		 return new PasswordAuthentication(username, password);
	 	}
 });

try {
 // Create a default MimeMessage object
 Message message = new MimeMessage(session);
 
 message.setFrom(new InternetAddress(from));
 
 message.setRecipients(Message.RecipientType.TO,
 InternetAddress.parse(to));
 
 // Set Subject
 message.setSubject("Hi JAXenter");
 
 // Put the content of your message
 message.setText("Hi there,we are just experimenting with JavaMail here");

// Send message
 Transport.send(message);

System.out.println("Sent message successfully....");

} catch (MessagingException e) {
 throw new RuntimeException(e);
 }
 }
}