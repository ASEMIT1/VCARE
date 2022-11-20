package com.vcare.entity;

import java.io.Serializable;

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

public class Employee implements Serializable{
	@Indexed
	@Id
	public String id;
	@Indexed
	String name;
	String mobile;
	String email;
	String address;
	String pan;
	String aadhar;
	String fatherName;
	boolean deleted;
	String role;
	String password;

}
