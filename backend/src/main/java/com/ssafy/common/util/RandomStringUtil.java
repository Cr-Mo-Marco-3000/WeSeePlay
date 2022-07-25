package com.ssafy.common.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomStringUtil {
	public String getRandomString(int randomLength) {
		String randomString="";
		for (int i = 0; i < randomLength; i++) {
			//아스키 코드 값 33~126
			randomString+=(char)(int)((Math.random()*94)+33);
		}
		return randomString;
	}
	
	public String getRandomNumber(int randomLength) {
		Random r=new Random();
		String randomString="";
		for (int i = 0; i < randomLength; i++) {
			randomString+=Integer.toString(r.nextInt(10));
		}
		return randomString;
	}
}
