package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.response.LiarWordGetRes;
import com.ssafy.api.service.GameService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 게임 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "게임 API", tags = {"Game"})
@RestController
@RequestMapping("/api/v1/game")
public class GameController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	GameService gameService;
	
	@GetMapping("/{liarGenre}")
	public ResponseEntity<? extends BaseResponseBody> getSuggestion(
			@ApiIgnore Authentication authentication, @PathVariable int liarGenre){
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		if(user==null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));
		}else {
			String suggestion=gameService.getWord(liarGenre);
			if(suggestion==null) {
				return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request"));
			}else {
				return ResponseEntity.status(200).body(LiarWordGetRes.of(200, "Success",suggestion));
			}
		}
	}
}
