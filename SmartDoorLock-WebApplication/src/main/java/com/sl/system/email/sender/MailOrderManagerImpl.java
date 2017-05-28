package com.sl.system.email.sender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailOrderManagerImpl implements MailOrderManager {
	
	@Autowired
	private JavaMailSender sender;
	
	@Override
	public void sendEmail(String to,String title, String msg) throws MessagingException {
		// TODO Auto-generated method stub
		MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(title);
        // use the true flag to indicate the text included is HTML
        helper.setText(msg,true);
        sender.send(message);
	}	
}
