package com.vcare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vcare.dto.Response;
import com.vcare.dto.UserData;
import com.vcare.dto.UserSignUpData;
import com.vcare.service.UserService;
import com.vcare.utils.JwtUtils;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserService service;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@Validated @RequestBody UserData user) {
		Response response = service.signIn(user);
		if (!response.getHttpStatus().equals(HttpStatus.NOT_FOUND)) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Set-Cookie", "token=" + response.getToken() + ";Path=/");
			headers.add("Set-Cookie", "role=" + response.getRole() + ";Path=/");
			return new ResponseEntity<>(response, headers, HttpStatus.OK);
		} else {
			ResponseEntity<?> respEntity = ResponseEntity.ok(response);
			return respEntity;
		}

	}

	@GetMapping("/logout")
	public ResponseEntity<?> logout() {
		Response response = new Response();
		response.setResponseMessage("Loggedout");
		response.setHttpStatus(HttpStatus.OK);
		ResponseEntity<?> respEntity = ResponseEntity.ok(response);
		return respEntity;

	}

//	@PostMapping("/signup")
//	public ResponseEntity<?> signUp(@Validated @RequestBody UserSignUpData user) {
//		Response response = service.saveUser(user);
//		ResponseEntity<?> respEntity = ResponseEntity.ok(response);
//		return respEntity;
//
//	}

}