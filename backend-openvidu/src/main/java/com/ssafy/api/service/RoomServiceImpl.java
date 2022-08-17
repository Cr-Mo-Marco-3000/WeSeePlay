package com.ssafy.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomUpdatePatchReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserRoom;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRoomRepository;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

	@Lazy
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	UserRoomRepository userRoomRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public static final String QUERY = "query";
	public static final String PAGENUMBER = "pageNumber";
	public static final String CALL_START_TIME = "callStartTime";
	public static final String CONTENTS_COUNT = "contentsCount";
	public static final String IS_PRIVATE = "isPrivate";
	
	@Override
	public Room createRoom(RoomCreatePostReq roomCreatePostReq) {
		Room room = new Room();
		room.setTitle(roomCreatePostReq.getTitle());
		room.setDescript(roomCreatePostReq.getDescript());
		if(roomCreatePostReq.getRoomPassword()==null ||roomCreatePostReq.getRoomPassword().equals("")) {
			room.setRoomPassword(null);
		}else {
			room.setRoomPassword(passwordEncoder.encode(roomCreatePostReq.getRoomPassword()));
		}
		room.setGame(roomCreatePostReq.getGame());
		room.setCallStartTime(LocalDateTime.now());
		room.setIsPrivate(roomCreatePostReq.getIsPrivate());
		room.setJoinCount(1);
		return roomRepository.save(room);
	}

	@Override
	public Boolean createUserRoom(Long roomId, Long userId, int isPlayer, int isHost) {
		UserRoom isUserInRoom=userRoomRepository.findByRoomIdAndUserId(roomId, userId);
		if(isUserInRoom==null) {
			UserRoom userRoom = new UserRoom();
			userRoom.setRoomId(roomId);
			userRoom.setUserId(userId);
			userRoom.setIsPlayer(isPlayer);
			userRoom.setIsHost(isHost);
			userRoomRepository.save(userRoom);
			return true;
		}
		return false;
	}

	@Override
	public void deleteRoom(long roomId) {
		roomRepository.deleteById(roomId);
	}

	@Override
	public Room getRoomById(long roomId) {
		return roomRepository.findRoomById(roomId);
	}

	@Override
	public Room updateRoom(int roomId,RoomUpdatePatchReq roomUpdatePatchReq) {
		Optional<Room> room = roomRepository.findById((long)roomId);
		if(!room.isPresent()) {
			return null;
		}
		if(roomUpdatePatchReq.getTitle()!=null &&!"".equals(roomUpdatePatchReq.getTitle())) {
			room.get().setTitle(roomUpdatePatchReq.getTitle());
		}
		if(roomUpdatePatchReq.getDescript()!=null) {
			room.get().setDescript(roomUpdatePatchReq.getDescript());
		}
		//공개방으로 전환
		if("".equals(roomUpdatePatchReq.getRoomPassword())) {
			room.get().setRoomPassword(null);
			room.get().setIsPrivate(0);
		}else if(roomUpdatePatchReq.getRoomPassword()!=null) {
			room.get().setRoomPassword(passwordEncoder.encode(roomUpdatePatchReq.getRoomPassword()));
			room.get().setIsPrivate(1);
		}
		if(roomUpdatePatchReq.getGame()!=0) {
			room.get().setGame(roomUpdatePatchReq.getGame());
		}
		return roomRepository.save(room.get());
	}

	@Override
	public List<Room> findAll() {
		return roomRepository.findAll();
	}

	@Override
	public Page<Room> getRoomList(Map<String, Object> map) {
		Pageable pageable=null;
		if("byTime".equals(map.get("sortingMethod"))) {
			if("toUp".equals(map.get("sortingOrder"))) {
				pageable=PageRequest.of(Integer.valueOf((String) map.get(PAGENUMBER))-1, 
						Integer.valueOf((String) map.get(CONTENTS_COUNT)),Sort.by(CALL_START_TIME));
			}else {
				pageable=PageRequest.of(Integer.valueOf((String) map.get(PAGENUMBER))-1, 
						Integer.valueOf((String) map.get(CONTENTS_COUNT)),Sort.by(CALL_START_TIME).descending());
			}
		}else if ("byUserNumByTime".equals(map.get("sortingMethod"))) {
			if("toUp".equals(map.get("sortingOrder"))) {
				pageable=PageRequest.of(Integer.valueOf((String) map.get(PAGENUMBER))-1, 
						Integer.valueOf((String) map.get(CONTENTS_COUNT)),Sort.by("joinCount").and(Sort.by(CALL_START_TIME).descending()));
			}else {
				pageable=PageRequest.of(Integer.valueOf((String) map.get(PAGENUMBER))-1, 
						Integer.valueOf((String) map.get(CONTENTS_COUNT)),Sort.by("joinCount").descending().and(Sort.by(CALL_START_TIME).descending()));
			}
		}else {
			return null;
		}
		Page<Room> room;
		
		if ("".equals(map.get(QUERY))) {
			if("0".equals(map.get(IS_PRIVATE))) {
				room=roomRepository.findAll(pageable);
			}else {
				room=roomRepository.findAllByIsPrivate(0,pageable);
			}
		}else {
			//방제로 검색
			if("1".equals(map.get("queryType"))) {
				if("0".equals(map.get(IS_PRIVATE))) {
					room=roomRepository.findAllByTitleContains((String)map.get(QUERY),pageable);
				}else {
					room=roomRepository.findAllByIsPrivateAndTitleContains(0,(String)map.get(QUERY),pageable);
				}
			//호스트 명으로 검색
			}else {
				List<User> users=userRepository.findAllByUserNicknameContains((String)map.get(QUERY));
				List<Long> userIds=new ArrayList<>();
				for (User user : users) {
					userIds.add(user.getId());
				}
				List<UserRoom>userRooms=userRoomRepository.findAllByIsHostAndUserIdIn(1,userIds);
				List<Long> roomIds=new ArrayList<>();
				for (UserRoom userRoom : userRooms) {
					roomIds.add(userRoom.getRoomId());
				}
				if("0".equals(map.get(IS_PRIVATE))) {
					room=roomRepository.findAllByIdIn(roomIds,pageable);
				}else {
					room=roomRepository.findAllByIsPrivateAndIdIn(0,roomIds,pageable);
				}
			}
		}
		return room;
	}

	@Override
	public void plus(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		if(room.isPresent()) {
			room.get().setJoinCount(room.get().getJoinCount()+1);
			roomRepository.save(room.get());
		}
	}

	@Override
	public void minus(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		if(room.isPresent()) {
			room.get().setJoinCount(room.get().getJoinCount()-1);
			roomRepository.save(room.get());
		}
	}

	@Override
	public void setMode(int roomId, int gameMode) {
		Optional<Room> room = roomRepository.findById((long) roomId);
		if(room.isPresent()) {
			room.get().setGame(gameMode);
			roomRepository.save(room.get());
		}
	}

	@Override
	public int getGameMode(int roomId) {
		Room room=roomRepository.findGameById(roomId);
		return room.getGame();
		
	}

}
