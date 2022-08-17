package com.ssafy.api.response;

import java.util.List;

import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CallMyNameGetResponse")
public class CallMyNameGetRes extends BaseResponseBody {
	List<String> wordList;
	
	public static CallMyNameGetRes of(Integer statusCode, String message, List<String> list) {
		CallMyNameGetRes res = new CallMyNameGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setWordList(list);
		return res;
	}
}
