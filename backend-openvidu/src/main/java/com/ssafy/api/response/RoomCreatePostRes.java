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
@ApiModel("RoomCreatePostResponse")
public class RoomCreatePostRes extends BaseResponseBody{
	
	Long roomId;
	
	public static RoomCreatePostRes of(Integer statusCode, String message, Long roomId) {
		RoomCreatePostRes res = new RoomCreatePostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setRoomId(roomId);
		return res;
	}
}
