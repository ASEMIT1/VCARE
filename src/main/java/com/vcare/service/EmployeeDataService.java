package com.vcare.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcare.dto.EmployeeEntryDataDTO;
import com.vcare.dto.EmployeeEntryDataForJobId;
import com.vcare.dto.EntryDataDTO;
import com.vcare.dto.EntryDataSearch;
import com.vcare.dto.NameIdMap;
import com.vcare.dto.Response;
import com.vcare.entity.CallCategory;
import com.vcare.entity.CallType;
import com.vcare.entity.Client;
import com.vcare.entity.Employee;
import com.vcare.entity.EmployeeEntryData;
import com.vcare.entity.EmployeeEntryDataSearch;
import com.vcare.entity.EntryData;
import com.vcare.entity.EntryHistoryData;
import com.vcare.repository.CallCategoryRepository;
import com.vcare.repository.CallTypeRepository;
import com.vcare.repository.ClientRepository;
import com.vcare.repository.EmployeeDataRepository;
import com.vcare.repository.EmployeeRepository;
import com.vcare.repository.EntryDataRepository;
import com.vcare.repository.EntryHistoryDataRepository;

@Service
public class EmployeeDataService {

	@Autowired
	CallTypeRepository repository;
	@Autowired
	CallCategoryRepository catRepo;
	@Autowired
	ClientRepository clientRepo;
	@Autowired
	EmployeeRepository empRepo;
	@Autowired
	EmployeeDataRepository dataRepo;
	@Autowired
	EntryDataRepository entryDataRepo;
	static Integer jobId = 0;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public Response saveEntryData(EmployeeEntryDataDTO entryData) {
		ObjectMapper obj = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			Response resposne = new Response();
			if (!checkEntries(entryData, resposne)) {
				logger.error("Failed to Save Entry Data");
				resposne.setHttpStatus(HttpStatus.NOT_FOUND);
				return resposne;
			}

			EmployeeEntryData data = obj.convertValue(entryData, EmployeeEntryData.class);

			EmployeeEntryData dataresp = dataRepo.save(data);
			resposne.setEmployeeEntryData(dataresp);
			resposne.setResponseMessage("success");
			resposne.setHttpStatus(HttpStatus.OK);
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Save Entry Data", e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}
	}

	private boolean checkEntries(EmployeeEntryDataDTO entryData, Response resposne) {
		if (!clientRepo.findById(entryData.getClientId()).isPresent()) {
			logger.error("Client Type ID is wrong:" + entryData.getClientId());
			resposne.setResponseMessage("Client is not present");
			return false;
		}

		if (!empRepo.findById(entryData.getEmployeeId()).isPresent()) {
			logger.error("Employee Type ID is wrong:" + entryData.getEmployeeId());
			resposne.setResponseMessage("Employee is not present");
			return false;
		}

		return true;

	}

	public Response getByIds(EntryDataSearch search) {

		String empId = "";
		Employee emp = empRepo.findByName(search.getEmployeeName());

		if (emp != null) {
			empId = emp.getId();
		}

		List<EmployeeEntryDataSearch> resp = entryDataRepo.findEntryDataForEmployeeByProperties(empId, "completed");
		Map<Integer, EmployeeEntryDataSearch> dataLIst = resp.stream().map(newDt -> {
			String empName = search.getEmployeeName();
			if (StringUtils.isEmpty(empName) || StringUtils.isEmpty(empName.trim())) {
				empName = empRepo.findById(newDt.getEmployeeId()).get().getName();
			}
			String clientName = clientRepo.findById(newDt.getClientId()).get().getName();
			String callTYpe = repository.findById(newDt.getCallTypeId()).get().getCallType();
			String catTYpe = catRepo.findById(newDt.getCategoryType()).get().getCallType();
			newDt.setEmployeeName(empName);
			newDt.setClientName(clientName);
			newDt.setCallType(callTYpe);
			newDt.setCategory(catTYpe);
			return newDt;
		}).collect(Collectors.toMap(EmployeeEntryDataSearch::getJobId, Function.identity()));
		Response response = new Response();
		response.setEmployeeDataList(dataLIst);
		return response;
	}
	
	public Response getByJobIds(int jobId) {
		ObjectMapper obj = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<EmployeeEntryData> employeeEntryData = dataRepo.findByJobId(jobId);
	
		List<EmployeeEntryDataForJobId> dataLIst = employeeEntryData.stream().map(newDt -> {
			EmployeeEntryDataForJobId data = obj.convertValue(newDt, EmployeeEntryDataForJobId.class);
			String empName = empRepo.findById(newDt.getEmployeeId()).get().getName();
			data.setEngineerName(empName);
			return data;
		}).collect(Collectors.toList());
		Response response = new Response();
		response.setDataForJobId(dataLIst);
		return response;
	}

}
