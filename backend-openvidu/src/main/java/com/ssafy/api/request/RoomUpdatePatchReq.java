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
@ApiModel("RoomUpdatePatchRequest")
public class RoomUpdatePatchReq {
	@ApiModelProperty(name="방 제목", example="친목방")
	String title;
	@ApiModelProperty(name="방 설명", example="어서오세요.")
	String descript;
	@ApiModelProperty(name="방 비밀번호", example="password")
	String roomPassword;
	@ApiModelProperty(name="호스트 ID", example="1")
	int hostId;
	@ApiModelProperty(name="방 상태", example="1, 2")
	int game;
	@ApiModelProperty(name="비공개 여부", example="0")
	int isPrivate;
}
