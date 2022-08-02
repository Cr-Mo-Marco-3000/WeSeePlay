package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.UserRoom;

public interface RoomService {

	Room createRoom(RoomCreatePostReq roomCreatePostReq);

	UserRoom createUserRoom(Long roomId, Long userId, int isPlayer, int isHost);

	void deleteRoom(long roomId);
	
	Room getRoomById(long roomId);

}
