package com.ssafy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.CallMyNameWord;

@Repository
public interface CallMyNameRepository extends JpaRepository<CallMyNameWord, Long>{
}
