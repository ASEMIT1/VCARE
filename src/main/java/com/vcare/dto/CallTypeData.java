package com.vcare.dto;

import javax.validation.constraints.NotEmpty;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CallTypeData {
	
	
	String id;
	@NonNull
	@NotEmpty
	String callType;
	

}
