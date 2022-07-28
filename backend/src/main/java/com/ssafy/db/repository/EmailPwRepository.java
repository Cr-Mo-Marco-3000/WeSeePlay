package com.ssafy.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.EmailPw;

public interface EmailPwRepository extends JpaRepository<EmailPw, Long>{
	Optional<EmailPw> findByUserEmail(String userEmail);

	void deleteByUserEmail(String userEmail);
}
