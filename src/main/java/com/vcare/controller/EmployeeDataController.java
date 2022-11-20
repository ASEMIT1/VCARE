package com.vcare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vcare.dto.EmployeeEntryDataDTO;
import com.vcare.dto.EntryDataSearch;
import com.vcare.dto.Response;
import com.vcare.service.EmployeeDataService;
import com.vcare.utils.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employeedata")
public class EmployeeDataController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmployeeDataService service;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/get")
	public ResponseEntity<?> getEmployeeData(@RequestBody EntryDataSearch dataSearch) {
		Response resp = service.getByIds(dataSearch);
		logger.info("Got Employee Data");
		resp.setResponseMessage("success");
		resp.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.ok(resp);

	}
	
	@GetMapping("/get/{jobId}")
	public ResponseEntity<?> getEmployeeData(@PathVariable int jobId) {
		Response resp = service.getByJobIds(jobId);
		logger.info("Got Employee Data");
		resp.setResponseMessage("success");
		resp.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.ok(resp);

	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveEmployeeData(@RequestBody EmployeeEntryDataDTO data) {
		Response resp = service.saveEntryData(data);
		logger.info("Got Employee Data");
		resp.setResponseMessage("success");
		resp.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.ok(resp);

	}

}