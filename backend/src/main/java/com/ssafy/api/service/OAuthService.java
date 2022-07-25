package com.ssafy.api.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.ssafy.db.entity.User;

public interface OAuthService {
	public String getKakaoAccessToken(String code);
	public JSONObject getKakaoUser(String token) throws Exception;
	public boolean userCheck(JSONObject jObject) throws JSONException;
	public User updateLoginTime(JSONObject jObject) throws JSONException;
	public User createUser(JSONObject jObject) throws JSONException;
}
