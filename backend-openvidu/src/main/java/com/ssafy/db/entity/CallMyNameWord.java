package com.ssafy.db.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "callmyname_word")
@Getter
@Setter
public class CallMyNameWord extends BaseEntity {
	String word;
}
