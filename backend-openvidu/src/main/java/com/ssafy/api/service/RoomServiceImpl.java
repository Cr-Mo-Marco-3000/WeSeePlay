package com.ssafy.api.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.UserRoom;
import com.ssafy.db.repository.RoomRepository;
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
	
	@Override
	public Room createRoom(RoomCreatePostReq roomCreatePostReq) {
		Room room = new Room();
		room.setTitle(roomCreatePostReq.getTitle());
		room.setDescript(roomCreatePostReq.getDescript());
		System.out.println(passwordEncoder);
		room.setRoomPassword(passwordEncoder.encode(roomCreatePostReq.getRoomPassword()));
		room.setGame(roomCreatePostReq.getGame());
		room.setCallStartTime(LocalDateTime.now());
		room.setIsPrivate(roomCreatePostReq.getIsPrivate());
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

}
