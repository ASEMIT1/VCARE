package com.vcare.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vcare.dto.ClientData;
import com.vcare.dto.EmployeeDataDTO;
import com.vcare.dto.EmployeeResponse;
import com.vcare.dto.Response;
import com.vcare.entity.Client;
import com.vcare.entity.ClientResponse;
import com.vcare.entity.Employee;
import com.vcare.service.ClientService;
import com.vcare.service.EmployeeService;
import com.vcare.utils.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmployeeService service;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/get")
	public ResponseEntity<?> getEmployeeData() {
		List<Employee> employeeData = service.getEmployeeData();
		logger.info("Got Employee Data");
		EmployeeResponse response = new EmployeeResponse();
		response.setEmplpoyeeData(employeeData);
		response.setResponseMessage("success");
		response.setStatus(HttpStatus.OK);
		return ResponseEntity.ok(response);

	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveEmployeeData(@RequestBody @Validated EmployeeDataDTO employeeData) {
		Response resp = service.saveEmployeeData(employeeData,false);
		return ResponseEntity.ok(resp);

	}
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody @Validated EmployeeDataDTO clientData) {
		Response resp = service.delete(clientData);
		
		return ResponseEntity.ok(resp);

	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editEmployeeData(@RequestBody @Validated EmployeeDataDTO employeeData) {
		if(StringUtils.isEmpty(employeeData.getId())) {
			Response resp = new Response();
			resp.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
			resp.setResponseMessage("ID is missing");
			return ResponseEntity.ok(resp);
		}
		Response resp = service.saveEmployeeData(employeeData,true);
		return ResponseEntity.ok(resp);

	}

}