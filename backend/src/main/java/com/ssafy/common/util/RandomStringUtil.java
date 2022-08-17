package com.ssafy.common.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomStringUtil {
	
	Random rand;
	
	public String getRandomString(int randomLength) {
		StringBuilder randomString=new StringBuilder();
		for (int i = 0; i < randomLength; i++) {
			//아스키 코드 값 33~126
			randomString.append((char)rand.nextInt(94)+33);
		}
		return randomString.toString();
	}
	
	public String getRandomNumber(int randomLength) {
		StringBuilder randomString=new StringBuilder();
		for (int i = 0; i < randomLength; i++) {
			randomString.append(Integer.toString(rand.nextInt(10)));
		}
		return randomString.toString();
	}
	
	public String getRandomPw(int randomLength) {
		StringBuilder randomString=new StringBuilder();
		char[][] randomValue= {{'1','2','3','4','5','6','7','8','9','0'},
				{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'},
				{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},
				{'!','@','#','$','%','&','(',')','?','/'}};
		int tempLength=0;
		int[] charCnt=new int[4];
		int temp;
		while(tempLength<randomLength) {
			temp=rand.nextInt(4);
			if(charCnt[temp]<3) {
				charCnt[temp]++;
				randomString.append(randomValue[temp][rand.nextInt(randomValue[temp].length)]);
				tempLength++;
			}
		}
		return randomString.toString();
	}
}
