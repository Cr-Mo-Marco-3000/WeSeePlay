package com.ssafy.common.util;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SendEmailUtil {
	
	@Autowired
	RandomStringUtil randomStringUtil;
	
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

        //인증 링크 클릭
        String mailContent="<h1>We See Play!!</h1><h2>[이메일 인증 확인]</h2><p>아래의 이메일 인증 확인을 눌러주세요.</p><br/>"+
        "<a href='http://localhost:8080/api/v1/users/email/certification?userEmail="+userEmail+"'target='_blank'>[이메일 인증 확인]</a>";
        
        message.setFrom(new InternetAddress(email));
 
            // 수신자 메일 주소
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
 
            // Subject
        message.setSubject("We See Play");
 
            // Text
        message.setText(mailContent, "utf-8", "html");
        Transport.send(message);    // send message

		return null;
	}
	
	public String SendPwEmail(String userEmail) throws MessagingException{
		String recipient=userEmail;
		Random rand = new Random();
		int pwLen=rand.nextInt(5)+8;
		String tempPw=randomStringUtil.getRandomPw(pwLen);
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

        //인증 링크 클릭
        String mailContent="<h1>We See Play!!</h1><h2>[임시 비밀번호 발송]</h2><p>아래의 비밀번호를 입력해서 로그인 해주세요.</p>"+
        tempPw+"<br><br>로그인 후 반드시 비밀번호를 변경해주세요"        ;
        
        message.setFrom(new InternetAddress(email));
 
            // 수신자 메일 주소
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
 
            // Subject
        message.setSubject("We See Play");
 
            // Text
        message.setText(mailContent, "utf-8", "html");
        Transport.send(message);    // send message

		return tempPw;
	}

	public static String SendEmailPw(String userEmail) throws MessagingException {
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

        //인증 링크 클릭
        String mailContent="<h1>We See Play!!</h1><h2>[이메일 인증 확인]</h2><p>아래의 이메일 인증 확인을 눌러주세요.</p><br/>"+
        "<a href='http://localhost:8080/api/v1/users/email/certification/pw?userEmail="+userEmail+"'target='_blank'>[이메일 인증 확인]</a>";
        
        message.setFrom(new InternetAddress(email));
 
            // 수신자 메일 주소
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
 
            // Subject
        message.setSubject("We See Play");
 
            // Text
        message.setText(mailContent, "utf-8", "html");
        Transport.send(message);    // send message

		return null;
	}
	
}
