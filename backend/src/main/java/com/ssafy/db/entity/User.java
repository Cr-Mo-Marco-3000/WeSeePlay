package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 유저 모델 정의.
 */
@Entity(name = "user")
@Getter
@Setter
@DynamicUpdate
public class User extends BaseEntity{
	@Column(name="user_email")
    String userEmail;
	
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="user_password")
    String userPassword;
    	
    @Column(name="user_nickname")
    String userNickname;

    @CreationTimestamp
    @Column(name="register_time", updatable = false)
    private LocalDateTime registerTime;
    
    @Column(name="last_login")
    private LocalDateTime lastLogin;
    
    @PrePersist
    public void beforeCreate() {
    	registerTime=LocalDateTime.now();
    	lastLogin=LocalDateTime.now();
    }
    
}
