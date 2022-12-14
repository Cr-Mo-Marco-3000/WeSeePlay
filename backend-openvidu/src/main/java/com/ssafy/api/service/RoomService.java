package com.ssafy.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomUpdatePatchReq;
import com.ssafy.db.entity.Room;

public interface RoomService {

	Room createRoom(RoomCreatePostReq roomCreatePostReq);

	Boolean createUserRoom(Long roomId, Long userId, int isPlayer, int isHost);

	void deleteRoom(long roomId);
	
	Room getRoomById(long roomId);

	Room updateRoom(int roomId, RoomUpdatePatchReq roomUpdatePatchReq);

	List<Room> findAll();

	Page<Room> getRoomList(Map<String, Object> map);

	void plus(Long roomId);
	
	void minus(Long roomId);

	void setMode(int roomId, int gameMode);

	int getGameMode(int roomId);
}
