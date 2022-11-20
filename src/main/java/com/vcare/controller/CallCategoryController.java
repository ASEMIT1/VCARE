package com.vcare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vcare.dto.CallTypeData;
import com.vcare.dto.Response;
import com.vcare.entity.CallCategory;
import com.vcare.entity.CallType;
import com.vcare.service.CallTypeService;
import com.vcare.utils.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CallCategoryController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	CallTypeService service;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/get")
	public ResponseEntity<?> getCategoryType() {
		List<CallCategory> clientsData = service.getCallCategory();
		logger.info("Got Client Data");
		Response response = new Response();
		response.setCatTypes(clientsData);
		response.setResponseMessage("success");
		return ResponseEntity.ok(response);

	}

	@PostMapping("/save")
	public ResponseEntity<?> saveCallTypeData(@RequestBody @Validated CallTypeData clientData) {
		Response resp = service.saveCategoryTypeData(clientData);

		return ResponseEntity.ok(resp);

	}
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody @Validated CallTypeData clientData) {
		Response resp = service.deleteCallCatType(clientData);
		
		return ResponseEntity.ok(resp);

	}

	@PostMapping("/edit")
	public ResponseEntity<?> editCallTypeData(@RequestBody @Validated CallTypeData clientData) {
		if (StringUtils.isEmpty(clientData.getId())) {
			Response resp = new Response();
			resp.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
			resp.setResponseMessage("ID is missing");
			return ResponseEntity.ok(resp);
		}
		Response resp = service.saveCategoryTypeData(clientData,true);
		return ResponseEntity.ok(resp);

	}

}