package com.ssafy.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
//import com.ssafy.db.repository.RoomRepository;

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
	
	@Override
	public Room createRoom(RoomCreatePostReq roomCreatePostReq) {
		Room room = new Room();
		room.setTitle(roomCreatePostReq.getTitle());
		room.setDescript(roomCreatePostReq.getDescript());
		room.setRoomPassword(passwordEncoder.encode(roomCreatePostReq.getRoomPassword()));
		room.setGame(roomCreatePostReq.getGame());
		room.setCallStartTime(LocalDateTime.now());
		room.setIsPrivate(roomCreatePostReq.getIsPrivate());
		room.setJoinCount(1);
		return roomRepository.save(room);
	}

	@Override
	public UserRoom createUserRoom(Long roomId, Long userId, int isPlayer, int isHost) {
		UserRoom userRoom = new UserRoom();
		userRoom.setRoomId(roomId);
		userRoom.setUserId(userId);
		userRoom.setIsPlayer(isPlayer);
		userRoom.setIsHost(isHost);
		return userRoomRepository.save(userRoom);
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
		if(roomUpdatePatchReq.getTitle()!=null) {
			room.get().setTitle(roomUpdatePatchReq.getTitle());
		}
		if(roomUpdatePatchReq.getDescript()!=null) {
			room.get().setDescript(roomUpdatePatchReq.getDescript());
		}
		//공개방으로 전환
		if(roomUpdatePatchReq.getRoomPassword()=="") {
			room.get().setRoomPassword("");
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
	public Page<Room> getRoomList(HashMap<String, Object> map) {
		Pageable pageable=null;
		if(map.get("sortingMethod").equals("byTime")) {
			if(map.get("sortingOrder").equals("toUp")) {
				pageable=PageRequest.of(Integer.valueOf((String) map.get("pageNumber"))-1, 
						Integer.valueOf((String) map.get("contentsCount")),Sort.by("callStartTime"));
			}else {
				pageable=PageRequest.of(Integer.valueOf((String) map.get("pageNumber"))-1, 
						Integer.valueOf((String) map.get("contentsCount")),Sort.by("callStartTime").descending());
			}
		}else if (map.get("sortingMethod").equals("byUserNumByTime")) {
			if(map.get("sortingOrder").equals("toUp")) {
				pageable=PageRequest.of(Integer.valueOf((String) map.get("pageNumber"))-1, 
						Integer.valueOf((String) map.get("contentsCount")),Sort.by("joinCount").and(Sort.by("callStartTime").descending()));
			}else {
				pageable=PageRequest.of(Integer.valueOf((String) map.get("pageNumber"))-1, 
						Integer.valueOf((String) map.get("contentsCount")),Sort.by("callStartTime").descending().and(Sort.by("callStartTime").descending()));
			}
		}
//		Pageable pageable=PageRequest.of(Integer.valueOf((String) map.get("pageNumber"))-1, 
//				Integer.valueOf((String) map.get("contentsCount")));
		Page<Room> room;
		
		if (map.get("query").equals("")) {
			if(map.get("isPrivate").equals("0")) {
				room=roomRepository.findAll(pageable);
			}else {
				room=roomRepository.findAllByIsPrivate(0,pageable);
			}
		}else {
			if(map.get("isPrivate").equals("0")) {
				room=roomRepository.findAllByTitleContains((String)map.get("query"),pageable);
				System.out.println(room.getSize());
			}else {
				room=roomRepository.findAllByIsPrivateAndTitleContains(0,(String)map.get("query"),pageable);
			}
			
//			//제목으로 검색
//			if(map.get("queryType").equals("1")) {
//				room=roomRepository.findAllByTitleContains((String)map.get("query"),pageable);
//			}else {
//				List<User> users=userRepository.findIdByUserNicknameContains((String)map.get("query"));
//				System.out.println(users.size());
//				List<Long> ids=new ArrayList<Long>();
//				for (User user : users) {
//					ids.add(user.getId());
//				}
//			}
		}
		return room;
	}

	@Override
	public void plus(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		room.get().setJoinCount(room.get().getJoinCount()+1);
		roomRepository.save(room.get());
	}

	@Override
	public void minus(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		room.get().setJoinCount(room.get().getJoinCount()-1);
		roomRepository.save(room.get());
	}

}
