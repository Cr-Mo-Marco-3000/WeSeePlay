package com.ssafy.common.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;


@Component
public class SendEmailUtil {
	private static String makeNum(){
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(String.valueOf((int)(Math.random() * 9)));
		}
		return sb.toString();
	}
	
	public static String SendEmail(String userEmail) throws MessagingException{
		String recipient=userEmail;
		
		final String email=SendEmailAccountInfoUtil.getEmail();
		final String password=SendEmailAccountInfoUtil.getEmailPassword();
		Properties prop=new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        
        MimeMessage message = new MimeMessage(session);
        
        String numCode=makeNum();

        //인증 링크 클릭
        String mailContent="<h2>[이메일 인증]</h2><br><p>아래의 이메일 인증 확인을 눌러주세요.</p><br/>"+
        "<a href='http://localhost:8080/api/v1/users/email/certification?userEmail="+userEmail+"'target='_blank'>[이메일 인증 확인]</a>";
        
        message.setFrom(new InternetAddress(email));
 
            // 수신자 메일 주소
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
 
            // Subject
        message.setSubject("이메일 인증 확인");
 
            // Text
        message.setText(mailContent, "utf-8", "html");
        Transport.send(message);    // send message

		return numCode;
	}
}
