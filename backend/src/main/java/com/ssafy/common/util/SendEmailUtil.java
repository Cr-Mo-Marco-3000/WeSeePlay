package com.ssafy.common.util;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SendEmailUtil {
	
	@Autowired
	RandomStringUtil randomStringUtil;
	
	Random rand;
	
	static String port="mail.smtp.port";
	static String host="mail.smtp.host";
	static String hostAddress="smtp.gmail.com";
	static String auth="mail.smtp.auth";
	static String subject="We See Play";
	static String enable="mail.smtp.ssl.enable";
	static String charSet="utf-8";
	static String trust="mail.smtp.ssl.trust";
	
	public static String sendEmail(String userEmail) throws MessagingException{
		String recipient=userEmail;
		
		final String email=SendEmailAccountInfoUtil.getEmail();
		final String password=SendEmailAccountInfoUtil.getEmailPassword();
		Properties prop=new Properties();
		prop.put(host, hostAddress);
        prop.put(port, 465);
        prop.put(auth, "true");
        prop.put(enable, "true");
        prop.put(trust, hostAddress);
		
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
        	@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        
        MimeMessage message = new MimeMessage(session);

        //인증 링크 클릭
        String mailContent="<h1>We See Play!!</h1><h2>[이메일 인증 확인]</h2><p>아래의 이메일 인증 확인을 눌러주세요.</p><br/>"+
        "<a href='https://i7a501.p.ssafy.io/api/v1/users/email/certification?userEmail="+userEmail+"'target='_blank'>[이메일 인증 확인]</a>";
        
        message.setFrom(new InternetAddress(email));
 
            // 수신자 메일 주소
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
 
            // Subject
        message.setSubject(subject);
 
            // Text
        message.setText(mailContent, charSet, "html");
        Transport.send(message);    // send message

		return null;
	}
	
	public String sendPwEmail(String userEmail) throws MessagingException{
		String recipient=userEmail;
		rand = new Random();
		int pwLen=rand.nextInt(5)+8;
		String tempPw=randomStringUtil.getRandomPw(pwLen);
		final String email=SendEmailAccountInfoUtil.getEmail();
		final String password=SendEmailAccountInfoUtil.getEmailPassword();
		Properties prop=new Properties();
		prop.put(host, hostAddress);
        prop.put(port, 465);
        prop.put(auth, "true");
        prop.put(enable, "true");
        prop.put(trust, hostAddress);
		
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
        	@Override
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
        message.setSubject(subject);
 
            // Text
        message.setText(mailContent, charSet, "html");
        Transport.send(message);    // send message

		return tempPw;
	}

	public static String sendEmailPw(String userEmail) throws MessagingException {
		String recipient=userEmail;
		
		final String email=SendEmailAccountInfoUtil.getEmail();
		final String password=SendEmailAccountInfoUtil.getEmailPassword();
		Properties prop=new Properties();
		prop.put(host, hostAddress);
        prop.put(port, 465);
        prop.put(auth, "true");
        prop.put(enable, "true");
        prop.put(trust, hostAddress);
		
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
        	@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        
        MimeMessage message = new MimeMessage(session);

        //인증 링크 클릭
        String mailContent="<h1>We See Play!!</h1><h2>[이메일 인증 확인]</h2><p>아래의 이메일 인증 확인을 눌러주세요.</p><br/>"+
        "<a href='https://i7a501.p.ssafy.io/api/v1/users/email/certification/pw?userEmail="+userEmail+"'target='_blank'>[이메일 인증 확인]</a>";
        
        message.setFrom(new InternetAddress(email));
 
            // 수신자 메일 주소
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
 
            // Subject
        message.setSubject(subject);
 
            // Text
        message.setText(mailContent, charSet, "html");
        Transport.send(message);    // send message

		return null;
	}
	
}
