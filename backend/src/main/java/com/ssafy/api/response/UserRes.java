package com.ssafy.api.response;

import java.time.LocalDateTime;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="User Email")
	String userEmail;
	@ApiModelProperty(name="User Nickname")
	String userNickname;
	@ApiModelProperty(name="register time")
	private LocalDateTime registerTime;
	@ApiModelProperty(name="last login")
	private LocalDateTime lastLogin;
	
	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setUserEmail(user.getUserEmail());
		res.setUserNickname(user.getUserNickname());
		res.setRegisterTime(user.getRegisterTime());
		res.setLastLogin(user.getLastLogin());
		return res;
	}
}
