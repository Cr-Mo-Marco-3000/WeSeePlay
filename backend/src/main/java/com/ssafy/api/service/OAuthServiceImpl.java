package com.ssafy.api.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.common.util.APIKeyUtil;
import com.ssafy.common.util.BCryptUtil;
import com.ssafy.common.util.RandomStringUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

@Service("oAuthService")
public class OAuthServiceImpl implements OAuthService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptUtil bCryptUtil;
	
	@Autowired
	RandomStringUtil randomStringUtil;
	
	@Override
	public String getKakaoAccessToken(String code) {
		String accessToken="";
		String refreshToken="";
		String reqURL="https://kauth.kakao.com/oauth/token";
		
		try {
			URL url=new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+new APIKeyUtil().getKakaoAPIKey());
            sb.append("&redirect_uri=http://localhost:8080/api/v1/oauth/kakao");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            
            //결과 코드가 200이라면 성공
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);
            
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            
            JSONObject jObject=new JSONObject(result);
            accessToken = jObject.getString("access_token");
            refreshToken = jObject.getString("refresh_token");

            br.close();
            bw.close();
            
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accessToken;
	}

	public JSONObject getKakaoUser(String token) throws Exception {

		String reqURL = "https://kapi.kakao.com/v2/user/me";
		String result = "";
	    //access_token을 이용하여 사용자 정보 조회
	    try {
	       URL url = new URL(reqURL);
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	       conn.setRequestMethod("POST");
	       conn.setDoOutput(true);
	       conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

	       //결과 코드가 200이라면 성공
	       int responseCode = conn.getResponseCode();

	       //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	       String line = "";

	       while ((line = br.readLine()) != null) {
	           result += line;
	       }
	       br.close();
	       } catch (Exception e) {
	            e.printStackTrace();
	       }
		return new JSONObject(result);
	 }

	@Override
	public boolean userCheck(JSONObject jObject) throws JSONException {
		//카카오 전용의 ID로 해당 유저 찾기
		String id = jObject.getString("id");
		return userRepository.findAllByUserEmail(id).isPresent();
	}

	@Override
	public User updateLoginTime(JSONObject jObject) throws JSONException {
		User user = new User();
		String id = jObject.getString("id");
		Optional<User>target=userRepository.findAllByUserEmail(id);
		if(target.get()!=null) {
			user.setId(target.get().getId());
			user.setUserEmail(target.get().getUserEmail());
			user.setUserPassword(bCryptUtil.encodeBcrypt(randomStringUtil.getRandomString(10)));
			user.setUserNickname(target.get().getUserNickname());
			user.setLastLogin(LocalDateTime.now());
			return userRepository.save(user);
		}
		return null;
	}

	@Override
	public User createUser(JSONObject jObject) throws JSONException {
		User user = new User();
		user.setUserEmail(jObject.getString("id"));
		user.setUserPassword(bCryptUtil.encodeBcrypt(randomStringUtil.getRandomString(10)));
		String userNickname=jObject.getJSONObject("kakao_account").getJSONObject("profile").getString("nickname");
		if (userNickname.length()<10) {
			user.setUserNickname(userNickname+randomStringUtil.getRandomNumber(5));
		}else if(userNickname.length()>=20) {
			user.setUserNickname(userNickname.substring(0, 19));
		}
		user.setRegisterTime(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());
		return userRepository.save(user);
	}
}
