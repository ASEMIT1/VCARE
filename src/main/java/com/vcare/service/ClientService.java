package com.vcare.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcare.dto.ClientData;
import com.vcare.dto.Response;
import com.vcare.entity.Client;
import com.vcare.entity.Employee;
import com.vcare.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository repository;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Client> getClientData() {
		logger.info("Getting Cliet Data");
		return repository.findClientByDeleted(false);
	}

	public Response saveClientData(ClientData clientData,boolean isEdit) {
		ObjectMapper obj = new ObjectMapper();
		try {
			Client client = obj.convertValue(clientData, Client.class);
			Client userExisting = repository.findByNameAndDeleted(clientData.getName(),false);

			Response resposne = new Response();
			if (userExisting == null || isEdit) {
				Client clientResp = repository.save(client);
				resposne.setClient(clientResp);
				resposne.setResponseMessage("success");
				resposne.setHttpStatus(HttpStatus.OK);
			} else {
				resposne.setResponseMessage("Client Already Exists with the same name");
				logger.error("Client Already Exists with the same name:" + clientData.getName());
				resposne.setHttpStatus(HttpStatus.BAD_REQUEST);
				return resposne;
			}
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Save", clientData.toString(), e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}
	public Response saveClientData(ClientData clientData) {
		return saveClientData(clientData,false);

	}

	public Response delete(ClientData clientData) {

		ObjectMapper obj = new ObjectMapper();
		try {
			Client client = obj.convertValue(clientData, Client.class);
			client.setDeleted(true);
			Client clientResp = repository.save(client);
			Response resposne = new Response();
			resposne.setResponseMessage("Deleted User");
			logger.info("Deleted Client:" + clientData.getName());
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
