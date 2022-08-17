package com.ssafy.db.repository;

import com.ssafy.db.entity.LiarWord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiarWordRepository extends JpaRepository<LiarWord, Long> {

	List<LiarWord> findAllByGenreId(int liarGenre);

}