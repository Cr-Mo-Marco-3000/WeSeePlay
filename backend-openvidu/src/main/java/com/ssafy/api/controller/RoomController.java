package com.ssafy.api.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.api.request.ForcedOutReq;
import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomUpdatePatchReq;
import com.ssafy.api.response.RoomCreatePostRes;
import com.ssafy.api.response.RoomListGetRes;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.service.UserService;
import com.ssafy.api.service.UserRoomService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserRoom;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 방 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "방 API", tags = {"Room"})
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	UserRoomService userRoomService;
	
	@PostMapping()
	@ApiOperation(value = "방 생성", notes = "방 생성에 대한 정보를 저장한다.") 
	@ApiResponses({
		@ApiResponse(code = 201, message = "성공"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	//일단 방 생성시 다른 방에 참여하고 있는 사용자인지 체크하진 않았음
	public ResponseEntity<? extends BaseResponseBody> createRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody @ApiParam(value="방 생성 정보", required = true) RoomCreatePostReq roomCreatePostReq) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		if(user!=null) {
			Room room=roomService.createRoom(roomCreatePostReq);
			roomService.createUserRoom(room.getId(),user.getId(),0,1);
			return ResponseEntity.status(201).body(RoomCreatePostRes.of(201, "Created",room.getId()));
		}else {
			return ResponseEntity.status(400).body(RoomCreatePostRes.of(400, "Bad Request"));
		}
	}
	
	@Transactional
	@DeleteMapping()
	@ApiOperation(value = "방 삭제", notes = "해당 방을 삭제한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public ResponseEntity<? extends BaseResponseBody> deleteRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody HashMap<String, Integer> map) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		Long userId=userService.getUserByUserEmail(userEmail).getId();
		UserRoom userRoomRes=userRoomService.getUserRoomByRoomId((long)map.get("roomId"),userId);
		if(userRoomRes != null && userRoomRes.getIsHost() == 1) {
			roomService.deleteRoom(map.get("roomId"));
			// roomId기준으로 다 방에서 퇴장 처리
			userRoomService.deleteUserRoom(map.get("roomId"));
			return ResponseEntity.status(200).body(RoomCreatePostRes.of(200, "Success"));
		}
		return ResponseEntity.status(400).body(RoomCreatePostRes.of(400, "Bad Request"));
	}
	
	@PostMapping("/enter")
	@ApiOperation(value = "방 입장", notes = "방에 입장한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public ResponseEntity<? extends BaseResponseBody> enterRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody HashMap<String, Integer> roomInfo) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		long roomId = (long) roomInfo.get("roomId");
		
		try {
			Room room = roomService.getRoomById(roomId);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No Room"));
		}
		
		if(user != null) {
			roomService.createUserRoom(roomId, user.getId(), 0, 0);
			roomService.plus(roomId);
		} else {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No User"));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@Transactional
	@DeleteMapping("/leave")
	@ApiOperation(value = "방 퇴장", notes = "방을 퇴장한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public ResponseEntity<? extends BaseResponseBody> leaveRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody HashMap<String, Integer> roomInfo) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		long roomId = (long) roomInfo.get("roomId");
		
		try {
			Room room = roomService.getRoomById(roomId);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No Room"));
		}
		
		if(user != null) {
			userRoomService.deleteUserRoom(roomId, user.getId());
			roomService.minus(roomId);
		} else {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No User"));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@Transactional
	@DeleteMapping("/forcedout")
	@ApiOperation(value = "방 강제퇴장", notes = "강제 퇴장한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public ResponseEntity<? extends BaseResponseBody> forcedOutRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody ForcedOutReq info) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		
		long roomId = info.getRoomId();
		User outUser = userService.getUserByUserEmail(info.getUserEmail());
		UserRoom userRoom = userRoomService.getUserRoomByRoomId(roomId, user.getId());
		
		try {
			Room room = roomService.getRoomById(roomId);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No Room"));
		}
		
		if(userRoom != null && userRoom.getIsHost() == 1) {
			userRoomService.deleteUserRoom(roomId, outUser.getId());
			roomService.minus(roomId);
		} else {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No UserRoom"));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@PatchMapping("{roomId}")
	@ApiOperation(value = "방 정보 수정", notes = "방 정보를 수정한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "권한 없음")
    })
	public ResponseEntity<? extends BaseResponseBody> updateRoom(
			@ApiIgnore Authentication authentication,
			@PathVariable int roomId, @RequestBody RoomUpdatePatchReq roomUpdatePatchReq) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		System.out.println(user.getId());
		int check=userRoomService.checkIsHost(user.getId(),(long)roomId);
		if (check==1) {
			roomService.updateRoom(roomId,roomUpdatePatchReq);
			if(roomUpdatePatchReq.getHostId()!=0) {
				userRoomService.setIsHost(roomUpdatePatchReq.getHostId(),roomId);
			}
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));
		}
	}
	
	@GetMapping()
	@ApiOperation(value = "방 리스트", notes = "방 리스트를 반환한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public String getRoomList(
			@ApiIgnore Authentication authentication, @RequestParam HashMap<String, Object>map ) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		List<Room> totalRoom=roomService.findAll();
		JsonObject jo=new JsonObject();
		jo.addProperty("totalData",totalRoom.size());
		List<Room> roomList=roomService.getRoomList(map);
		JsonArray jsonArray=new JsonArray();
		for (Room room : roomList) {
			JsonObject temp=new JsonObject();
			temp.addProperty("roomId", room.getId());
			
			UserRoom userRoom=userRoomService.getHostIdByRoomId(room.getId());
			User tempuser=userService.getUserById(userRoom.getUserId());
			temp.addProperty("hostId",userRoom.getUserId());
			temp.addProperty("hostNickname",tempuser.getUserEmail());
			temp.addProperty("callStartTime", room.getCallStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			temp.addProperty("title", room.getTitle());
			temp.addProperty("descript", room.getDescript());
			temp.addProperty("isPrivate", room.getIsPrivate());
			temp.addProperty("game", room.getGame());
			
			List<UserRoom>tempUserRoom=userRoomService.getMemberIdByRoomId(room.getId());
			JsonArray userInfos=new JsonArray();
			for (UserRoom data : tempUserRoom) {
				JsonObject info=new JsonObject();
				User tempUser=userService.getUserById(data.getUserId());
				info.addProperty("userId", tempUser.getId());
				info.addProperty("userEmail", tempUser.getUserEmail());
				info.addProperty("userNickname", tempUser.getUserNickname());
				userInfos.add(info);
			}
			temp.add("joinUsers", userInfos);
			jsonArray.add(temp);
		}
		
		jo.add("content", jsonArray);
		jo.addProperty("statusCode", 200);
		jo.addProperty("message", "Success");
		System.out.println(jo.toString());
//		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		
		return jo.toString();
//		return ResponseEntity.status(200).body(RoomListGetRes.of(200, "Success",totalRoom.size(),jsonArray));
	}
}
