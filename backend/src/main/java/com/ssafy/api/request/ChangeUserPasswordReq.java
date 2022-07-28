package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 비밀번호 변경 API ([PATCH] /api/v1/users/password) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ChangeUserPasswordReq")
public class ChangeUserPasswordReq {
	@ApiModelProperty(name="유저 Email", example="ssafy_web@ssafy.com")
	String userEmail;
	@ApiModelProperty(name="기존 비밀번호", example="your_password")
	String userPassword;
	@ApiModelProperty(name="변경할 비밀번호", example="your_new_password")
	String userNewPassword;
}
