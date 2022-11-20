package com.vcare.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcare.dto.ClientData;
import com.vcare.dto.EmployeeDataDTO;
import com.vcare.dto.Response;
import com.vcare.entity.Client;
import com.vcare.entity.Employee;
import com.vcare.entity.User;
import com.vcare.repository.ClientRepository;
import com.vcare.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Employee> getEmployeeData() {
		logger.info("Getting Employee Data");
		return repository.findByDeleted(false);

	}

	public Response saveEmployeeData(EmployeeDataDTO employeeData, boolean isEdit) {
		ObjectMapper obj = new ObjectMapper();
		try {
			Employee employee = obj.convertValue(employeeData, Employee.class);
			Employee userExisting = repository.findByName(employeeData.getName());
			Response resposne = new Response();
			if (userExisting == null || isEdit) {
				Employee empResp = repository.save(employee);

				resposne.setEmployee(empResp);
				resposne.setResponseMessage("success");
				resposne.setHttpStatus(HttpStatus.OK);
			}else {
				resposne.setResponseMessage("Employee Already Exists with the same name");
				logger.error("Employee Already Exists with the same name:"+ employeeData.getName());
				resposne.setHttpStatus(HttpStatus.BAD_REQUEST);
				return resposne;
			}
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Save", employeeData.toString(), e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}

	public Response delete(EmployeeDataDTO employeeData) {
		ObjectMapper obj = new ObjectMapper();
		try {
			Employee employee = obj.convertValue(employeeData, Employee.class);
			employee.setDeleted(true);
			Employee empResp = repository.save(employee);

			Response resposne = new Response();
			resposne.setResponseMessage("Deleted Employee");
			logger.info("Deleted Employee:" + employeeData.getName());
			resposne.setHttpStatus(HttpStatus.OK);
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Delete", employeeData.toString(), e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}
}
