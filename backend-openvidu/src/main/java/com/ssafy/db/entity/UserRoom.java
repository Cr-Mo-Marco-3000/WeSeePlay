package com.ssafy.db.entity;


import javax.persistence.Entity;
import org.hibernate.annotations.DynamicUpdate;


import lombok.Getter;
import lombok.Setter;

@Entity(name = "user_room")
@Getter
@Setter
@DynamicUpdate
public class UserRoom extends BaseEntity{
	long roomId;
	long userId;
	int isPlayer;
	int isHost;
}
