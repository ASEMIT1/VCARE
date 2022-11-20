package com.vcare.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

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
public class ClientData implements Serializable {
	
	String id;
	@NotNull
	@NotEmpty
	String name;
	@NotNull
	@NotEmpty
	String mobile;
	@Nullable
	String email;
	@NotNull
	@NotEmpty
	String address;

}
