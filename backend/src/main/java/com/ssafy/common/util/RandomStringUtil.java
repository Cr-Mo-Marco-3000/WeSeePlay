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
	
	public String getRandomPw(int randomLength) {
		String randomString="";
		char[][] randomValue= {{'1','2','3','4','5','6','7','8','9','0'},
				{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'},
				{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},
				{'!','@','#','$','%','&','(',')','?','/'}};
		int tempLength=0;
		int[] charCnt=new int[4];
		
		while(tempLength<randomLength) {
			
			tempLength++;
		}
		
		return randomString;
	}
}
