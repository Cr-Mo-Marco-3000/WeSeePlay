package com.ssafy.db.repository;

import com.ssafy.db.entity.LiarGenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiarGenreRepository extends JpaRepository<LiarGenre, Long> {
	

}