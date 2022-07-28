package com.ssafy.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptUtil {
	public String encodeBcrypt(String userPw) {
		return new BCryptPasswordEncoder().encode(userPw);
	}
	
									//입력받은 PW,  유저의 인코딩된 PW
	public boolean matchesBcrypt(String inputPw, String userPw) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(inputPw, userPw);
	}
}
