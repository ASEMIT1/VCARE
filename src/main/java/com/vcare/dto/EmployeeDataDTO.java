package com.vcare.dto;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDataDTO implements Serializable{
	
	String id;
	@NotNull
	@NotEmpty
	String name;
	@NotNull
	@NotEmpty
	String mobile;
	@NotNull
	@NotEmpty
	String email;
	@NotNull
	@NotEmpty
	String address;
	@NotNull
	@NotEmpty
	String pan;
	@NotNull
	@NotEmpty
	String aadhar;
	@NotNull
	@NotEmpty
	String fatherName;
	@NotNull
	@NotEmpty
	String password;
	@NotNull
	@NotEmpty
	String role;

}
