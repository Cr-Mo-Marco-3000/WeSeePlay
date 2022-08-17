package com.ssafy.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "liar_word")
@Getter
@Setter
public class LiarWord extends BaseEntity{
	@Column(name="genre_id")
	int genreId;
	
	String word;
}
