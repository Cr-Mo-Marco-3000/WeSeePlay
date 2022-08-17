package com.ssafy.api.controller;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ssafy.api.request.ChangeNicknameReq;
import com.ssafy.api.request.ChangeUserPasswordReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserPwGetRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.RandomStringUtil;
import com.ssafy.common.util.SendEmailUtil;
import com.ssafy.db.entity.Email;
import com.ssafy.db.entity.EmailPw;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SendEmailUtil sendEmailUtil;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RandomStringUtil randomStringUtil;
	
	Random rand = new Random();
	
	static String success="Success";
	static String unauthorized="Unauthorized";
	
	@GetMapping("/email/duplicate/check")
	@ApiOperation(value = "중복 이메일 확인", notes = "<strong>중복 체크를 실행한다.") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 409, message = "존재하는 이메일"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> duplicateCheck(
			@RequestParam @ApiParam(value="이메일 주소", required = true) String userEmail) {
		
		//중복 아이디가 있는지 확인
		boolean existUser=userService.duplicateCheck(userEmail);
		if(existUser) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "Exist Email"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, success));
	}
	
	@PostMapping("/email/certification")
	@ApiOperation(value = "이메일 확인", notes = "<strong>이메일 인증을 실행한다.") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 400, message = "주소 오류"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> emailCertification(
			@RequestBody @ApiParam(value="이메일 주소", required = true) UserRegisterPostReq registerInfo) {
		try {
			userService.createCertificationCheck(registerInfo.getUserEmail());
			SendEmailUtil.sendEmail(registerInfo.getUserEmail());
		} catch (AddressException e) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "AddressException"));
		} catch (MessagingException e) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "MessagingException"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, success));
	}
	
	@PostMapping("/email/certification/pw")
	@ApiOperation(value = "이메일 인증", notes = "비밀번호 찾기를 위한 이메일 인증을 실시한다.") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 400, message = "주소 오류"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> emailCertificationPw(
			@RequestBody @ApiParam(value="이메일 주소", required = true) UserRegisterPostReq registerInfo) {
		boolean existUser=userService.duplicateCheck(registerInfo.getUserEmail());
		if(existUser) {
			try {
				userService.createCertificationCheckPw(registerInfo.getUserEmail());
				SendEmailUtil.sendEmailPw(registerInfo.getUserEmail());
			} catch (AddressException e) {
				return ResponseEntity.status(400).body(BaseResponseBody.of(400, "AddressException"));
			} catch (MessagingException e) {
				return ResponseEntity.status(400).body(BaseResponseBody.of(400, "MessagingException"));
			}
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, success));
		}
		return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not Exist Email"));
	}
	
	@GetMapping("/email/certification")
	@ApiOperation(value = "이메일 인증 링크", notes = "<strong>이메일 인증을 실행한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public RedirectView updateCertification(
			@RequestParam (value="userEmail", required = true) String userEmail) {
	
		userService.updateCertification(userEmail);

		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new RedirectView("/");
	}
	
	@GetMapping("/email/certification/pw")
	@ApiOperation(value = "이메일 인증 링크", notes = "<strong>이메일 인증을 실행한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public RedirectView updateCertificationPw(
			@RequestParam (value="userEmail", required = true) String userEmail) {
	
		userService.updateCertificationPw(userEmail);

		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new RedirectView("/");
	}
	
	@GetMapping("/email/certification/check")
	@ApiOperation(value = "이메일 인증 확인", notes = "<strong>이메일 인증을 실행한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseBody> certificationCheck(
			@RequestParam (value="userEmail", required = true) String userEmail) {
	
		Optional<Email> email = userService.certificationCheck(userEmail);
		if(email.isPresent() && email.get().getCertificationCheck().equals("1")) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, success));
		}else {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, unauthorized));
		}
	}
	
	@PutMapping("/email/certification/pw/check")
	@ApiOperation(value = "이메일 인증 확인", notes = "<strong>이메일 인증을 실행한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseBody> certificationPwCheck(
			@RequestBody Map<String, String> map) {
		String email="userEmail";
		Optional<EmailPw> emailPw = userService.certificationPwCheck(map.get(email));
		if(emailPw.isPresent() && emailPw.get().getCertificationCheck().equals("1")) {
			int pwLen=rand.nextInt(5)+8;
			String tempPw=randomStringUtil.getRandomPw(pwLen);
			userService.updatePassword(map.get(email),tempPw);
			userService.delCertificationPw(map.get(email));
			
			return ResponseEntity.status(201).body(UserPwGetRes.of(200, success,tempPw));
		}else {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, unauthorized));
		}
	}
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "가입 완료"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo){

		Optional<Email> email = userService.certificationCheck(registerInfo.getUserEmail());
//		인증된 이메일일 때
		if(email.isPresent() && email.get().getCertificationCheck().equals("1")) {
			boolean check=true;
			if(registerInfo.getUserPassword().length()<8 || registerInfo.getUserPassword().length()>16) {
				check=false;
			}
			int length=registerInfo.getUserNickname().getBytes().length;
			if(check && length<6 || length>24) {
				check=false;
			}
			if(check) {
//				지우고 user테이블에 data추가
				userService.delCertification(email.get());
				User user = userService.createUser(registerInfo);
				if(user != null) {
					return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Created"));
				}else {
					return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Server Error"));
				}
			}else {
				return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Bad Request"));
			}
		}
		//인증되지 않은 이메일일 때
		return ResponseEntity.status(401).body(BaseResponseBody.of(401, unauthorized));
	}
	
	//본인 정보 조회
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		
		return ResponseEntity.status(200).body(UserRes.of(user));
	}
	
	@PatchMapping("/nickname")
	@ApiOperation(value = "닉네임 변경", notes = "닉네임을 변경한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseBody> changeNickname(@RequestBody @ApiParam(value="변경 요청 정보", required = true) ChangeNicknameReq changeInfo) {
		try {
			User user = userService.changeNickname(changeInfo.getUserEmail(), changeInfo.getUserNewNickname());
			if (user == null) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "이메일에 해당하는 유저가 없습니다")); // 로그인된 사용자만 사용하기 때문에 필요할지
		} catch (Exception e) {
			return ResponseEntity.status(404).body(BaseResponseBody.of(500, "닉네임 변경 실패"));
		}
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "닉네임 변경 성공"));
	}
	
	@PatchMapping("/password")
	@ApiOperation(value = "비밀번호 변경", notes = "비밀번호를 변경한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "비밀번호 다름"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseBody> changeUserPassword(@RequestBody @ApiParam(value="변경 요청 정보", required = true) ChangeUserPasswordReq changeInfo) {
		String userEmail = changeInfo.getUserEmail();
		String userPassword = changeInfo.getUserPassword();
		
		User user = null;
		try {
			user = userService.getUserByUserEmail(userEmail);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body(UserLoginPostRes.of(404, "존재하지 않는 계정입니다.", null));
		}
		
		if (passwordEncoder.matches(userPassword, user.getUserPassword())) {
			// 새로운 비밀번호로 변경
			userService.changePassword(changeInfo);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "비밀번호 변경 성공"));
		} else {
			// 기존 비밀번호가 맞지 않으므로 에러
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "비밀번호가 맞지 않습니다."));
		}
		
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(value = "회원 탈퇴", notes = "회원을 삭제한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
        @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class)
    })
	public ResponseEntity<BaseResponseBody> delete(@ApiIgnore Authentication authentication) {
		if(authentication==null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, unauthorized));
		}
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		int check=userService.deleteUser(userEmail);
		if(check==0) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Not Exist User"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, success));
	}
}
