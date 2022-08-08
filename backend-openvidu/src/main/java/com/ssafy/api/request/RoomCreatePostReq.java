package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 방 생성 API ([POST] /api/v1/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("RoomCreatePostRequest")
public class RoomCreatePostReq {
	@ApiModelProperty(name="방 제목", example="친목방")
	String title;
	@ApiModelProperty(name="방 설명", example="어서오세요.")
	String descript;
	@ApiModelProperty(name="방 비밀번호", example="password", required = false)
	String roomPassword;
	@ApiModelProperty(name="방 상태", example="0, 1")
	int game;
	@ApiModelProperty(name="비공개 여부", example="0")
	int isPrivate;
}
