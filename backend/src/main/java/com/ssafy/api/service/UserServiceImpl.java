package com.ssafy.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.api.request.ChangeUserPasswordReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.Email;
import com.ssafy.db.entity.EmailPw;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.EmailPwRepository;
import com.ssafy.db.repository.EmailRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	EmailPwRepository emailPwRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserEmail(userRegisterInfo.getUserEmail());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setUserPassword(passwordEncoder.encode(userRegisterInfo.getUserPassword()));
		user.setUserNickname(userRegisterInfo.getUserNickname());
		user.setRegisterTime(LocalDateTime.now());
		user.setLastLogin(null);
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserEmail(String userEmail) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		Optional<User> user = userRepositorySupport.findUserByUserEmail(userEmail);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public boolean duplicateCheck(String userEmail) {
		Optional<User>user=userRepository.findAllByUserEmail(userEmail);
		return user.isPresent();
	}
	
	@Override
	public Email createCertificationCheck(String userEmail) {
		Email email=new Email();
		Optional<Email> target = emailRepository.findByUserEmail(userEmail);
		if(target.isPresent()) {
			email.setCertificationCheck("0");
			email.setUserEmail(userEmail);
			email.setId(target.get().getId());
			email.setRegisterTime(LocalDateTime.now());
		}else {
			email.setUserEmail(userEmail);
			email.setCertificationCheck("0");
		}
		return emailRepository.save(email);
	}
	
	@Override
	public EmailPw createCertificationCheckPw(String userEmail) {
		EmailPw emailPw=new EmailPw();
		Optional<User> target = userRepository.findUserByUserEmail(userEmail);
		//계정이 존재할 경우 temp 테이블 확인 후 이메일 발송
		if(target.isPresent()) {
			Optional<EmailPw> target2=emailPwRepository.findByUserEmail(userEmail);
			//이미 temp 테이블에 있을 때
			if (target2.isPresent()) {
				emailPw.setId(target2.get().getId());
				
			}
			emailPw.setCertificationCheck("0");
			emailPw.setUserEmail(userEmail);
			emailPw.setRegisterTime(LocalDateTime.now());
			return emailPwRepository.save(emailPw);
		//계정이 없다면 오류 메시지 반환
		}
		return null;
	}
	
	@Override
	public Optional<Email> certificationCheck(String userEmail) {
		return emailRepository.findByUserEmail(userEmail);
	}
	
	@Override
	public Optional<EmailPw> certificationPwCheck(String userEmail) {
		// TODO Auto-generated method stub
		return emailPwRepository.findByUserEmail(userEmail);
	}

	@Override
	public void delCertification(Email userEmail) {
		emailRepository.delete(userEmail);
		
	}

	@Override
	public Email updateCertification(String userEmail) {
		Email email=new Email();
		Optional<Email> target = emailRepository.findByUserEmail(userEmail);
		if(target.get()!=null) {
			email.setId(target.get().getId());
			email.setUserEmail(userEmail);
			email.setCertificationCheck("1");
			email.setRegisterTime(LocalDateTime.now());
			return emailRepository.save(email);
		}
		return null;
	}

	@Override
	public EmailPw updateCertificationPw(String userEmail) {
		EmailPw emailPw=new EmailPw();
		Optional<EmailPw> target=emailPwRepository.findByUserEmail(userEmail);
		if(target.get()!=null) {
			emailPw.setId(target.get().getId());
			emailPw.setUserEmail(userEmail);
			emailPw.setCertificationCheck("1");
			emailPw.setRegisterTime(LocalDateTime.now());
			return emailPwRepository.save(emailPw);
		}
		return null;
	}
	
	@Override
	public User updateLastLogin(String userEmail) {
		User user = userRepository.findUserByUserEmail(userEmail).get();
		
		if (user != null) {
			user.setLastLogin(LocalDateTime.now());
			return userRepository.save(user);
		}
		
		return null;
	}

	@Override
	public User updatePassword(String userEmail, String tempPw) {
		Optional<User> user = userRepositorySupport.findUserByUserEmail(userEmail);
		if(user.isPresent()) {
			User updateUser = new User();
			updateUser.setId(user.get().getId());
			updateUser.setUserEmail(userEmail);
			// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
			updateUser.setUserPassword(passwordEncoder.encode(tempPw));
			updateUser.setUserNickname(user.get().getUserNickname());
			return userRepository.save(updateUser);
		}
		return null;
	}

	@Override
	public User changeNickname(String userEmail, String userNewNickname) {
		User user = userRepository.findUserByUserEmail(userEmail).get();
		
		if (user != null) {
			user.setUserNickname(userNewNickname);
			return userRepository.save(user);
		}
		
		return null;
	}

	@Override
	public User changePassword(ChangeUserPasswordReq changeInfo) {
		User user = userRepository.findUserByUserEmail(changeInfo.getUserEmail()).get();
		
		if (user != null) {
			user.setUserPassword(passwordEncoder.encode(changeInfo.getUserNewPassword()));
			return userRepository.save(user);
		}
		
		return null;
	}

	@Override
	@Transactional
	public void delCertificationPw(String userEmail) {
		emailPwRepository.deleteByUserEmail(userEmail);
	}
}
