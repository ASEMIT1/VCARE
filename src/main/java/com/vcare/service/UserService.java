package com.vcare.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vcare.dto.Response;
import com.vcare.dto.UserData;
import com.vcare.entity.Employee;
import com.vcare.entity.User;
import com.vcare.repository.EmployeeRepository;
import com.vcare.utils.JwtUtils;
import com.vcare.utils.RoleAuthMapping;

@Service
public class UserService {
	
	public static List<String> tokenList = new ArrayList<>();

	@Autowired
	EmployeeRepository repository;
	@Autowired
	JwtUtils jwtUtils;
//	@Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

	Logger logger = LoggerFactory.getLogger(this.getClass());

//	public Response saveUser(UserSignUpData userData) {
//		ObjectMapper obj = new ObjectMapper();
//		try {
//			User user = obj.convertValue(userData, User.class);
//			user.setPassword(/* bCryptPasswordEncoder.encode( */user.getPassword()/* ) */);
//			User userExisting = repository.findByName(userData.getName());
//			if(userExisting!=null) {
//				Response resposne = new Response();
//				resposne.setResponseMessage("User Already Exists with the same name");
//				logger.error("User Already Exists with the same name:"+ userData.getName());
//				resposne.setHttpStatus(HttpStatus.BAD_REQUEST);
//				return resposne;
//			}
//			repository.save(user);
//			Response resposne = new Response();
//			resposne.setResponseMessage("User is created");
//			resposne.setHttpStatus(HttpStatus.OK);
//			return resposne;
//		} catch (Exception e) {
//			Response resposne = new Response();
//			resposne.setResponseMessage("Failed to create User");
//			logger.error("Failed to Create User", e);
//			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
//			return resposne;
//		}
//
//	}

	public Response signIn(UserData userData) {
		try {
			Employee user = repository.findByName(userData.getName());
			if (!user.getPassword().equals(/* bCryptPasswordEncoder.encode( */userData.getPassword()/* ) */)) {
				Response resposne = new Response();
				resposne.setResponseMessage("Password does not match for user : " + user.getName());
				logger.error("Password does not match for user : " + user.getName());
				resposne.setHttpStatus(HttpStatus.NOT_FOUND);
				return resposne;
			}else if(user.isDeleted()) {
				Response resposne = new Response();
				resposne.setResponseMessage("User Does not exist : " + user.getName());
				logger.error("User Does not exist : " + user.getName());
				resposne.setHttpStatus(HttpStatus.NOT_FOUND);
				return resposne;
			}
			else {
				String token = jwtUtils.generateJwtToken(user);
				Response resposne = new Response();
				resposne.setResponseMessage("User is Logged IN");
				resposne.setHttpStatus(HttpStatus.OK);
				resposne.setToken(token);
				resposne.setRole(jwtUtils.getRoleFromJwtToken(token));
				resposne.setCreateUser(RoleAuthMapping.createUserRole.contains(resposne.getRole()));
				resposne.setEditEnabled(RoleAuthMapping.editRole.contains(resposne.getRole()));
				tokenList.add(token);
				return resposne;
			}
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("Failed to Login User");
			logger.error("Failed to Login User", e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}
}
