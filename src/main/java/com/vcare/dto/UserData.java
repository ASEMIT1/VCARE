package com.vcare.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
	
	@NonNull
	@NotEmpty
	String name;
	@NonNull
	@NotEmpty
	String password;
	

}
