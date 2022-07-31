package com.ssafy.api.service;

import java.util.List;

import com.ssafy.db.entity.UserRoom;

public interface UserRoomService {

	List<UserRoom> getUserRoomByRoomId(Long roomId, Long userId);

	void deleteUserRoom(long roomId, long userId);
}
