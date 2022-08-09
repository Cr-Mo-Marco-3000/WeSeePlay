package com.ssafy.db.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "liar_genre")
@Getter
@Setter
public class LiarGenre extends BaseEntity{
	String genre;
}
