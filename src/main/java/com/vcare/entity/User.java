package com.vcare.entity;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	String id;
	@NonNull
	@NotEmpty
	String name;
	@NonNull
	@NotEmpty
	String password;
	
	@NonNull
	@NotEmpty
	String role;
	

}
