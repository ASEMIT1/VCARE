package com.vcare.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcare.dto.CallTypeData;
import com.vcare.dto.ClientData;
import com.vcare.dto.EmployeeDataDTO;
import com.vcare.dto.Response;
import com.vcare.entity.CallCategory;
import com.vcare.entity.CallType;
import com.vcare.entity.Client;
import com.vcare.repository.CallCategoryRepository;
import com.vcare.repository.CallTypeRepository;
import com.vcare.repository.ClientRepository;

@Service
public class CallTypeService {

	@Autowired
	CallTypeRepository repository;
	@Autowired
	CallCategoryRepository catRepo;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<CallType> getCallType() {
		logger.info("Getting CallType Data");
		return repository.findByDeleted(false);

	}

	public List<CallCategory> getCallCategory() {
		logger.info("Getting CallType Data");
		return catRepo.findByDeleted(false);

	}

	public Response deleteCallType(CallTypeData clientData) {
		ObjectMapper obj = new ObjectMapper();
		try {
			CallType client = obj.convertValue(clientData, CallType.class);
			client.setDeleted(true);
			CallType clientResp = repository.save(client);
			Response resposne = new Response();
			resposne.setResponseMessage("Deleted CallTypeData");
			logger.info("Deleted CallType:" + clientData.getCallType());
			resposne.setHttpStatus(HttpStatus.OK);
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Delete CallTypeData", clientData.toString(), e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}

	public Response deleteCallCatType(CallTypeData clientData) {
		ObjectMapper obj = new ObjectMapper();
		try {
			CallCategory client = obj.convertValue(clientData, CallCategory.class);
			client.setDeleted(true);
			CallCategory clientResp = catRepo.save(client);
			Response resposne = new Response();
			resposne.setResponseMessage("Deleted CallCategory");
			logger.info("Deleted CallCategory:" + clientData.getCallType());
			resposne.setHttpStatus(HttpStatus.OK);
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Delete CallCategory", clientData.toString(), e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}
	public Response saveCategoryTypeData(CallTypeData clientData) {
		return saveCategoryTypeData(clientData,false);
	}
	public Response saveCategoryTypeData(CallTypeData clientData,boolean isEdit) {
		ObjectMapper obj = new ObjectMapper();
		try {
			List<CallCategory> result = catRepo.findByCallType(clientData.getCallType());
			if ((result != null && !result.isEmpty())&& !isEdit) {
				Optional<CallCategory> opt = result.stream().filter(call -> !call.isDeleted()).findAny();
				if (opt.isPresent()) {
					Response resposne = new Response();
					resposne.setResponseMessage("Category Type already exists");
					logger.error("Failed to Save", clientData.toString());
					resposne.setHttpStatus(HttpStatus.NOT_FOUND);
					return resposne;
				}
			}
			CallCategory client = obj.convertValue(clientData, CallCategory.class);
			CallCategory clientResp = catRepo.save(client);

			Response resposne = new Response();
			resposne.setCatType(clientResp);
			resposne.setResponseMessage("success");
			resposne.setHttpStatus(HttpStatus.OK);
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Save", clientData.toString(), e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}
	public Response saveCallTypeData(CallTypeData clientData) {
		return saveCallTypeData(clientData, false);
	}
	public Response saveCallTypeData(CallTypeData clientData,boolean isEdit) {
		ObjectMapper obj = new ObjectMapper();
		try {
			List<CallType> result = repository.findByCallType(clientData.getCallType());
			if ((result != null && !result.isEmpty())&& !isEdit) {
				Optional<CallType> opt = result.stream().filter(call -> !call.isDeleted()).findAny();
				if (opt.isPresent()) {
					Response resposne = new Response();
					resposne.setResponseMessage("Call Type already exists");
					logger.error("Failed to Save", clientData.toString());
					resposne.setHttpStatus(HttpStatus.NOT_FOUND);
					return resposne;
				}
			}
			CallType client = obj.convertValue(clientData, CallType.class);
			CallType clientResp = repository.save(client);

			Response resposne = new Response();
			resposne.setCallType(clientResp);
			resposne.setResponseMessage("success");
			resposne.setHttpStatus(HttpStatus.OK);
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Save", clientData.toString(), e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}
}
