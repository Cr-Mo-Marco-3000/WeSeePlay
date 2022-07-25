package com.ssafy.api.service;

import java.util.Optional;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.Email;
import com.ssafy.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserEmail(String userEmail);
	boolean duplicateCheck(String userEmail);
	Email createCertificationCheck(String userEmail);
	Optional<Email> certificationCheck(String userEmail);
	void delCertification(Email userEmail);
	Email updateCertification(String userEmail);
	User updateLastLogin(String userEmail);
	User updatePassword(String userEmail, String tempPw);
}
