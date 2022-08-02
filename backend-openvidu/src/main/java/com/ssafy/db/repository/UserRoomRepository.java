package com.ssafy.db.repository;

import com.ssafy.db.entity.UserRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {

	Optional<UserRoom> findByRoomIdAndUserId(Long roomId, Long userId);

	void deleteByRoomIdAndUserId(long roomId, long userId);
	void deleteByRoomId(long roomId);
}