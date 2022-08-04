package com.ssafy.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "room")
@Getter
@Setter
@DynamicUpdate
public class Room extends BaseEntity{
	String title;
	String descript;
	String roomPassword;
	int game;
	@Column(updatable = false)
	LocalDateTime callStartTime;
	int isPrivate;
	@Column(name="join_count")
	int joinCount;
}
