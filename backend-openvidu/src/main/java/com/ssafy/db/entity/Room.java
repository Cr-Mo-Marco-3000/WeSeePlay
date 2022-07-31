package com.ssafy.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	LocalDateTime callStartTime;
	int isPrivate;
}
