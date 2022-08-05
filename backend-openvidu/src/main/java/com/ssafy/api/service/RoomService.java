package com.ssafy.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomUpdatePatchReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.UserRoom;

public interface RoomService {

	Room createRoom(RoomCreatePostReq roomCreatePostReq);

	UserRoom createUserRoom(Long roomId, Long userId, int isPlayer, int isHost);

	void deleteRoom(long roomId);
	
	Room getRoomById(long roomId);

	Room updateRoom(int roomId, RoomUpdatePatchReq roomUpdatePatchReq);

	List<Room> findAll();

	Page<Room> getRoomList(HashMap<String, Object> map);

	void plus(Long roomId);
	
	void minus(Long roomId);
}
