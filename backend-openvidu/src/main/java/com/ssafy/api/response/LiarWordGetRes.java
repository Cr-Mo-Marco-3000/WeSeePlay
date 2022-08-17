package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 방 생성 API ([POST] /api/v1/rooms) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("LiarWordGetResponse")
public class LiarWordGetRes extends BaseResponseBody{
	
	String suggestion;
	
	public static LiarWordGetRes of(Integer statusCode, String message, String suggestion) {
		LiarWordGetRes res = new LiarWordGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setSuggestion(suggestion);
		return res;
	}
}
