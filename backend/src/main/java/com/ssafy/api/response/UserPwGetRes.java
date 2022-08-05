package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserPwGetResponse")
public class UserPwGetRes extends BaseResponseBody{
	String password;
	
	public static UserPwGetRes of(Integer statusCode, String message, String password) {
		UserPwGetRes res = new UserPwGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setPassword(password);
		return res;
	}
}
