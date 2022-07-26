package com.ssafy.api.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.OAuthService;
import com.ssafy.common.util.JwtTokenUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthController {
	
	@Autowired
	OAuthService oAuthService;
	
	@GetMapping("/kakao")
	@ApiOperation(value = "카카오 Code", notes = "카카오 로그인 후 반환되는 Code를 받아온다.")
	//테스트를 위해 Front 부분 일부 구현
	public ResponseEntity<?> KakaoLogin(@RequestParam String code) {
		//Code가 아니라 token을 바로 받아 오는것으로 수정 필요
		String token=oAuthService.getKakaoAccessToken(code);
		String userId="";
		try {
			JSONObject jObject=oAuthService.getKakaoUser(token);
			userId=jObject.getString("id");
			//존재하는 경우 로그인 시간 변경
			if(oAuthService.userCheck(jObject)) {
				oAuthService.updateLoginTime(jObject);
			//존재하지 않는 경우 계정 추가
			}else {
				oAuthService.createUser(jObject);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(500).body(UserLoginPostRes.of(500, "Server Error", null));
		}
		return ResponseEntity.status(200).body(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
	}
}