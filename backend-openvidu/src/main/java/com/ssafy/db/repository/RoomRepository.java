package com.ssafy.db.repository;

import com.ssafy.db.entity.Room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // 아래와 같이, Query Method 인터페이스(반환값, 메소드명, 인자) 정의를 하면 자동으로 Query Method 구현됨.
//    Optional<Room> findRoomByTitle(String title);
	Room findRoomById(long roomId);

	Page<Room> findAllByTitleContains(String query, Pageable pageable);

	Page<Room> findAllByIsPrivate(int i, Pageable pageable);

	Page<Room> findAllByIsPrivateAndTitleContains(int i, String string, Pageable pageable);

	Room findGameById(long roomId);
}