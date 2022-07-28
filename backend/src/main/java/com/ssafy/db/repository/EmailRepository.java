package com.ssafy.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Email;
import com.ssafy.db.entity.EmailPw;

public interface EmailRepository extends JpaRepository<Email, Long>{

	Optional<Email> findByUserEmail(String userEmail);
}
