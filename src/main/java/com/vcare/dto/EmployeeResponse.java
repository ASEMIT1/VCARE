package com.vcare.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.vcare.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
	
	private List<Employee> emplpoyeeData;
	private String responseMessage;
	private HttpStatus status;

}
