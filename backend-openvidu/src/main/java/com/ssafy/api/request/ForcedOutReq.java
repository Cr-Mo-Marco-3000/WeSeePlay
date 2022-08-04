package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ForcedOutReq")
public class ForcedOutReq {
	@ApiModelProperty(name="room ID", example="13")
	long roomId;
	@ApiModelProperty(name="유저 Email", example="email")
	String userEmail;
}
