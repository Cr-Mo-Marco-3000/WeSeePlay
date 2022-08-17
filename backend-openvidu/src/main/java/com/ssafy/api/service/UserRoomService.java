package com.ssafy.api.service;

import java.util.List;

import com.ssafy.db.entity.UserRoom;

public interface UserRoomService {

	UserRoom getUserRoomByRoomId(Long roomId, Long userId);

	Boolean deleteUserRoom(long roomId, long userId);
	void deleteUserRoom(long roomId);

	UserRoom setIsHost(int hostId,int roomId);

	int checkIsHost(Long userId, Long roomId);

	UserRoom getHostIdByRoomId(Long roomId);

	List<UserRoom> getMemberIdByRoomId(Long roomId);

	int getJoinCount(long roomId);

	int isExistUser(int hostId, int roomId);
}
