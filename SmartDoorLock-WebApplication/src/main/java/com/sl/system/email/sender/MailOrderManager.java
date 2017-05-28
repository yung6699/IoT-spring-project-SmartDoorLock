package com.sl.system.email.sender;

import javax.mail.MessagingException;

public interface MailOrderManager {
	void sendEmail(String to,String title, String msg) throws MessagingException;
}
