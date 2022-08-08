package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 닉네임 변경 API ([PATCH] /api/v1/users/nickname) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
public class KakaoPostReq {
	String accessToken;
	String test;
}
