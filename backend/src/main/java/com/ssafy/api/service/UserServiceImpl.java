package com.ssafy.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.Email;
import com.ssafy.db.entity.User;
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
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		System.out.println(userRegisterInfo.toString());
		user.setUserEmail(userRegisterInfo.getUserEmail());
		System.out.println(userRegisterInfo.getUserPassword());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setUserPassword(passwordEncoder.encode(userRegisterInfo.getUserPassword()));
		user.setUserNickname(userRegisterInfo.getUserNickname());
		user.setRegisterTime(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());
		System.out.println("user 정보");
		System.out.println(user.getUserEmail());
		System.out.println(user.getUserPassword());
		System.out.println(user.getUserNickname());
		System.out.println(user.getRegisterTime());
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserEmail(String userEmail) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		Optional<User> user = userRepositorySupport.findUserByUserEmail(userEmail);
//		Optional<User> user=userRepository.findUserByUserId(userId);
//		Optional<User> user=userRepository.findByUserId(userId);
		System.out.println("user_id = "+userEmail);
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
			LocalDateTime registertime=LocalDateTime.now();
			email.setRegisterTime(registertime);
			System.out.println(registertime);
		}else {
			System.out.println("22222");
			email.setUserEmail(userEmail);
			email.setCertificationCheck("0");
		}
		return emailRepository.save(email);
	}
	
	@Override
	public Optional<Email> certificationCheck(String userEmail) {
		return emailRepository.findByUserEmail(userEmail);
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
	public User updateLastLogin(String userEmail) {
		User user = userRepository.findUserByUserEmail(userEmail).get();
		
		if (user != null) {
			user.setLastLogin(LocalDateTime.now());
			return userRepository.save(user);
		}
		
		return null;
	}

}
