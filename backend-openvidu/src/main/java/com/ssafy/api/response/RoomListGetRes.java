package com.ssafy.api.response;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RoomListGetResponse")
public class RoomListGetRes extends BaseResponseBody{
	int totalData;
	JsonArray content;
	public static RoomListGetRes of(Integer statusCode, String message, int totalData, JsonArray jsonArray) {
		RoomListGetRes res=new RoomListGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setTotalData(totalData);
		res.setContent(jsonArray);
		return res;
	}
}
