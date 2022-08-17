package com.ssafy.common.util;

import org.springframework.stereotype.Component;

@Component
public class APIKeyUtil {
	private static final String REST_API_KEY = "6a87359832fd67a6e8e051eafefea636";
	public String getKakaoAPIKey() {
		return REST_API_KEY;
	}
}
