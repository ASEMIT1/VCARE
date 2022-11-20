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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vcare.dto.ClientData;
import com.vcare.dto.Response;
import com.vcare.entity.Client;
import com.vcare.entity.ClientResponse;
import com.vcare.service.ClientService;
import com.vcare.utils.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/client")
public class ClientController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	ClientService service;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/get")
	public ResponseEntity<?> getClientData() {
		List<Client> clientsData = service.getClientData();
		logger.info("Got Client Data");
		ClientResponse response = new ClientResponse();
		response.setClientData(clientsData);
		response.setResponseMessage("success");
		response.setStatus(HttpStatus.OK);
		return new ResponseEntity<>(response,  HttpStatus.OK);

	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveClientData(@RequestBody @Validated ClientData clientData) {
		Response resp = service.saveClientData(clientData);
		
		return new ResponseEntity<>(resp,  HttpStatus.OK);

	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteClientData(@RequestBody @Validated ClientData clientData) {
		Response resp = service.delete(clientData);
		
		return new ResponseEntity<>(resp,  HttpStatus.OK);

	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editClientData(@RequestBody @Validated ClientData clientData) {
		if(StringUtils.isEmpty(clientData.getId())) {
			Response resp = new Response();
			resp.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
			resp.setResponseMessage("ID is missing");
			return ResponseEntity.ok(resp);
		}
		Response resp = service.saveClientData(clientData,true);
		return new ResponseEntity<>(resp,  HttpStatus.OK);

	}

//	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
//
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//		redisUtil.setData(loginRequest.getUsername(), jwt, "user");
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//
//		return ResponseEntity.ok(
//				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
//	}
//
//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signUpRequest) {
//
//		// Create new user's account
//		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
//				encoder.encode(signUpRequest.getPassword()));
//
//		String strRoles = signUpRequest.getRole();
//		if (strRoles == null) {
//			return ResponseEntity.ok(new MessageResponse("Role is must!"));
//		}
//		try {
//			ERole role = ERole.valueOf(strRoles);
//			user.setRole(role);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Role is invalid!"));
//		}
//		userRepository.save(user);
//
//		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//	}
}