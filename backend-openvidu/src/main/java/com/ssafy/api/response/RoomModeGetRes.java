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
@ApiModel("RoomModeGetResponse")
public class RoomModeGetRes extends BaseResponseBody{
	
	int gameMode;
	
	public static RoomModeGetRes of(Integer statusCode, String message, int gameMode) {
		RoomModeGetRes res = new RoomModeGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setGameMode(gameMode);
		return res;
	}
}
