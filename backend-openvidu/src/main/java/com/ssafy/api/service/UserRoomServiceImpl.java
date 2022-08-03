package com.ssafy.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.UserRoom;
import com.ssafy.db.repository.UserRoomRepository;

@Service("userRoomService")
public class UserRoomServiceImpl implements UserRoomService{

	@Autowired
	UserRoomRepository userRoomRepository;
	
	@Override
	public UserRoom getUserRoomByRoomId(Long roomId, Long userId) {
		return userRoomRepository.findByRoomIdAndUserId(roomId, userId);
	}

	@Override
	public void deleteUserRoom(long roomId, long userId) {
		userRoomRepository.deleteByRoomIdAndUserId((int)roomId,(int)userId);
	}

	@Override
	public void deleteUserRoom(long roomId) {
		userRoomRepository.deleteByRoomId(roomId);
	}

	@Override
	public UserRoom setIsHost(int hostId, int roomId) {
		UserRoom userRoom=userRoomRepository.findByRoomIdAndIsHost((long)roomId,1);
		userRoom.setIsHost(0);
		userRoomRepository.save(userRoom);
		userRoom=userRoomRepository.findByRoomIdAndUserId((long)roomId,(long)hostId);
		userRoom.setIsHost(1);
		return userRoomRepository.save(userRoom);
	}

	@Override
	public int checkIsHost(Long userId, Long roomId) {
		UserRoom userRoom=userRoomRepository.findByRoomIdAndUserId(roomId, userId);
		return userRoom.getIsHost();
	}

}
