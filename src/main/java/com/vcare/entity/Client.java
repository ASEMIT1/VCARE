package com.vcare.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Client implements Serializable{
	
	@Indexed
	@Id
	public String id;
	@Indexed
	String name;
	String mobile;
	String email;
	String address;
	boolean deleted;

}
