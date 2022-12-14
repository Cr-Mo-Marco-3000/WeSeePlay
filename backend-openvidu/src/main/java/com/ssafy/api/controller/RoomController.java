package com.ssafy.api.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.ssafy.api.response.RoomModeGetRes;
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
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public static final String ROOMID = "roomId";
	public static final String MSG = "message";
	public static final String STATUS_CODE = "statusCode";
	public static final String IS_PRIVATE = "isPrivate";
	public static final String SUCCESS = "Success";
	public static final String INPUT_PASSWORD = "inputPassword";
	public static final String BAD_REQUEST = "Bad Request";
	boolean check;
	
	@PostMapping()
	@ApiOperation(value = "방 생성", notes = "방 생성에 대한 정보를 저장한다.") 
	@ApiResponses({
		@ApiResponse(code = 201, message = "성공"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	//일단 방 생성시 다른 방에 참여하고 있는 사용자인지 체크하진 않았음
	public ResponseEntity<BaseResponseBody> createRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody @ApiParam(value="방 생성 정보", required = true) RoomCreatePostReq roomCreatePostReq) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		if(roomCreatePostReq.getTitle()==null || roomCreatePostReq.getTitle().equals("")) {
			return ResponseEntity.status(400).body(RoomCreatePostRes.of(400, BAD_REQUEST));
		}
		if(user!=null) {
			Room room=roomService.createRoom(roomCreatePostReq);
			roomService.createUserRoom(room.getId(),user.getId(),0,1);
			return ResponseEntity.status(201).body(RoomCreatePostRes.of(201, "Created",room.getId()));
		}else {
			return ResponseEntity.status(400).body(RoomCreatePostRes.of(400, BAD_REQUEST));
		}
	}
	
	@Transactional
	@DeleteMapping()
	@ApiOperation(value = "방 삭제", notes = "해당 방을 삭제한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public ResponseEntity<BaseResponseBody> deleteRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody Map<String, Integer> map) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		Long userId=userService.getUserByUserEmail(userEmail).getId();
		UserRoom userRoomRes=userRoomService.getUserRoomByRoomId((long)map.get(ROOMID),userId);
		if(userRoomRes != null && userRoomRes.getIsHost() == 1) {
			roomService.deleteRoom(map.get(ROOMID));
			// roomId기준으로 다 방에서 퇴장 처리
			userRoomService.deleteUserRoom(map.get(ROOMID));
			return ResponseEntity.status(200).body(RoomCreatePostRes.of(200, SUCCESS));
		}else if(userRoomRes != null && userRoomRes.getIsHost() == 0) {
			return ResponseEntity.status(401).body(RoomCreatePostRes.of(401, "Unauthorized"));
		}else {
			return ResponseEntity.status(400).body(RoomCreatePostRes.of(400, BAD_REQUEST));
		}
	}
	
	@PostMapping("/enter")
	@ApiOperation(value = "방 입장", notes = "방에 입장한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public ResponseEntity<BaseResponseBody> enterRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody Map<String, String> roomInfo) {
		Room room=null;
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		long roomId = Long.parseLong(roomInfo.get(ROOMID));
		room = roomService.getRoomById(roomId);
		if(room==null) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No Room"));
		}
		if(user != null) {
			int count = userRoomService.getJoinCount(roomId);
			if(count<12 && room.getGame()==1) {
				if((roomInfo.get(INPUT_PASSWORD)==null || "".equals(roomInfo.get(INPUT_PASSWORD))) && room.getRoomPassword()==null) {
					check=roomService.createUserRoom(roomId, user.getId(), 0, 0);
					if(check) {
						roomService.plus(roomId);
					}
				}else if((roomInfo.get(INPUT_PASSWORD)==null || "".equals(roomInfo.get(INPUT_PASSWORD)))&& room.getRoomPassword()!=null ||roomInfo.get(INPUT_PASSWORD)!=null && !"".equals(roomInfo.get(INPUT_PASSWORD)) && room.getRoomPassword()==null ) {
					return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - Bad Password"));
				}
				else if(passwordEncoder.matches(roomInfo.get(INPUT_PASSWORD),room.getRoomPassword())){
					check=roomService.createUserRoom(roomId, user.getId(), 0, 0);
					if(check) {
						roomService.plus(roomId);						
					}
				}
				else {
					return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - Bad Password"));
				}
			}else if(room.getGame()!=1) {
				return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - Can't Enter Game Room"));
			}
			else {
				return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - Too Many People"));
			}
		} else {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No User"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, SUCCESS));
	}
	
	@Transactional
	@DeleteMapping("/leave")
	@ApiOperation(value = "방 퇴장", notes = "방을 퇴장한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public ResponseEntity<BaseResponseBody> leaveRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody Map<String, Integer> roomInfo) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		long roomId = roomInfo.get(ROOMID);
		try {
			roomService.getRoomById(roomId);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No Room"));
		}
		if(user != null) {
			check=userRoomService.deleteUserRoom(roomId, user.getId());
			if(check) {
				roomService.minus(roomId);
			}else {
				return ResponseEntity.status(400).body(BaseResponseBody.of(400, BAD_REQUEST));
			}
		} else {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No User"));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, SUCCESS));
	}
	
	@Transactional
	@DeleteMapping("/forcedout")
	@ApiOperation(value = "방 강제퇴장", notes = "강제 퇴장한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공")
    })
	public ResponseEntity<BaseResponseBody> forcedOutRoom(
			@ApiIgnore Authentication authentication,
			@RequestBody ForcedOutReq info) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		
		long roomId = info.getRoomId();
		User outUser = userService.getUserByUserEmail(info.getUserEmail());
		UserRoom userRoom = userRoomService.getUserRoomByRoomId(roomId, user.getId());
		
		try {
			roomService.getRoomById(roomId);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No Room"));
		}
		
		if(userRoom != null && userRoom.getIsHost() == 1) {
			userRoomService.deleteUserRoom(roomId, outUser.getId());
			roomService.minus(roomId);
		} else {
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Bad Request - No UserRoom"));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, SUCCESS));
	}
	
	@Transactional
	@PatchMapping("{roomId}")
	@ApiOperation(value = "방 정보 수정", notes = "방 정보를 수정한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "권한 없음")
    })
	public ResponseEntity<BaseResponseBody> updateRoom(
			@ApiIgnore Authentication authentication,
			@PathVariable int roomId, @RequestBody RoomUpdatePatchReq roomUpdatePatchReq) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userEmail);
		int tempCheck=userRoomService.checkIsHost(user.getId(),(long)roomId);
		if (tempCheck==1) {
			if(roomUpdatePatchReq.getHostId()!=0) {
				int temp=userRoomService.isExistUser(roomUpdatePatchReq.getHostId(),roomId);
				if(temp!=0) {
					userRoomService.setIsHost(roomUpdatePatchReq.getHostId(),roomId);
				}else {
					return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not Joined User"));
				}
			}
			roomService.updateRoom(roomId,roomUpdatePatchReq);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, SUCCESS));
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
			@ApiIgnore Authentication authentication, @RequestParam Map<String, Object>map ) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		userService.getUserByUserEmail(userEmail);
		JsonObject jo=new JsonObject();
		if(map.get("pageNumber")==null || map.get("contentsCount")==null || map.get(IS_PRIVATE)==null || map.get("sortingOrder")==null
				 || map.get("sortingMethod")==null || map.get("query")==null || map.get("queryType")==null) {
			jo.addProperty(STATUS_CODE,400);
			jo.addProperty(MSG,BAD_REQUEST);
			return  jo.toString();
		}
		
		Page<Room> totalRoomList=roomService.getRoomList(map);
		List<Room> roomList=totalRoomList.getContent();
		JsonArray jsonArray=new JsonArray();
		jo.addProperty("totalPage",totalRoomList.getTotalPages());
		jo.addProperty("totalData",totalRoomList.getTotalElements());
		for (Room room : roomList) {
			JsonObject temp=new JsonObject();
			temp.addProperty(ROOMID, room.getId());
			
			UserRoom userRoom=userRoomService.getHostIdByRoomId(room.getId());
			User tempuser=userService.getUserById(userRoom.getUserId());
			temp.addProperty("hostId",userRoom.getUserId());
			temp.addProperty("hostEmail",tempuser.getUserEmail());
			temp.addProperty("hostNickname",tempuser.getUserNickname());
			temp.addProperty("callStartTime", room.getCallStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			temp.addProperty("title", room.getTitle());
			temp.addProperty("descript", room.getDescript());
			temp.addProperty(IS_PRIVATE, room.getIsPrivate());
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
		jo.addProperty(STATUS_CODE, 200);
		jo.addProperty(MSG, SUCCESS);
		return jo.toString();
	}
	
	@GetMapping("/info/{roomId}")
	@ApiOperation(value = "단일 방 정보", notes = "단일 방 정보를 반환한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "권한 없음")
    })
	public String getRoomInfo(
			@ApiIgnore Authentication authentication,
			@PathVariable int roomId) {
		Room room=roomService.getRoomById(roomId);
		if(room!=null) {
			JsonObject temp=new JsonObject();
			temp.addProperty(ROOMID, room.getId());
			
			UserRoom userRoom=userRoomService.getHostIdByRoomId(room.getId());
			User tempuser=userService.getUserById(userRoom.getUserId());
			temp.addProperty("hostId",userRoom.getUserId());
			temp.addProperty("hostEmail",tempuser.getUserEmail());
			temp.addProperty("hostNickname",tempuser.getUserNickname());
			temp.addProperty("callStartTime", room.getCallStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			temp.addProperty("title", room.getTitle());
			temp.addProperty("descript", room.getDescript());
			temp.addProperty(IS_PRIVATE, room.getIsPrivate());
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
			temp.addProperty(STATUS_CODE, 200);
			temp.addProperty(MSG, SUCCESS);
			return temp.toString();
		}else {
			JsonObject error=new JsonObject();
			error.addProperty(STATUS_CODE,404);
			error.addProperty(MSG,"Deleted Room");
			return error.toString();
		}
	}
	
	@GetMapping("/mode/{roomId}")
	@ApiOperation(value = "방 모드 반환", notes = "방 모드를 반환한다.(1은 채팅, 2는 라이어 게임)") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "권한 없음")
    })
	public ResponseEntity<BaseResponseBody> updateMode(
			@ApiIgnore Authentication authentication,
			@PathVariable int roomId) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userEmail = userDetails.getUsername();
		userService.getUserByUserEmail(userEmail);
		try {
			int gameMode=roomService.getGameMode(roomId);
			return ResponseEntity.status(200).body(RoomModeGetRes.of(200, SUCCESS,gameMode));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Not Exist Room"));
		}
	}
}
