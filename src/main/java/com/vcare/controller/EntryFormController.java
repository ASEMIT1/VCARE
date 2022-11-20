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

import com.vcare.dto.EntryDataDTO;
import com.vcare.dto.EntryDataSearch;
import com.vcare.dto.Response;
import com.vcare.service.EntryDataService;
import com.vcare.utils.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/entry")
public class EntryFormController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EntryDataService service;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/get")
	public ResponseEntity<?> getEntryData() {
		Response response = service.getEntryBaseData();
		logger.info("getEntryData");
		response.setResponseMessage("success");
		return ResponseEntity.ok(response);

	}
	
	@GetMapping("/get/data/{page}")
	public ResponseEntity<?> getPage(@PathVariable("page") int page) {
		Response response = service.getAllByPage(page);
		logger.info("getEntryData");
		response.setResponseMessage("success");
		return ResponseEntity.ok(response);

	}

	@PostMapping("/save")
	public ResponseEntity<?> saveEntryData(@RequestBody EntryDataDTO entryData) {
		Response response = service.saveEntryData(entryData);
		logger.info("savingEntryData");

		if (response.getEntryData() == null) {
			logger.info("Failed Saving Entry Data");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
		} else {
			response.setResponseMessage("success");
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editEntryData(@RequestBody EntryDataDTO entryData) {
		Response response = service.editEntryData(entryData);
		logger.info("EditingEntryData");

		if (response.getEntryData() == null) {
			logger.info("Failed Saving Entry Data");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
		} else {
			response.setResponseMessage("success");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PostMapping("/search/{page}")
	public ResponseEntity<?> search(@RequestBody EntryDataSearch entryData, @PathVariable int page) {
		Response response = service.getByIds(entryData,page);
		logger.info("savingEntryData");

		if (response.getEntryDataList() == null) {
			logger.info("No Data found");
			response.setResponseMessage("No Data Found");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
		} else {
			response.setResponseMessage("success");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}