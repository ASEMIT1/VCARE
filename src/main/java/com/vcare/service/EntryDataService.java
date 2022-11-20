package com.vcare.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcare.dto.EntryDataDTO;
import com.vcare.dto.EntryDataSearch;
import com.vcare.dto.NameIdMap;
import com.vcare.dto.Response;
import com.vcare.entity.CallCategory;
import com.vcare.entity.CallType;
import com.vcare.entity.Client;
import com.vcare.entity.Employee;
import com.vcare.entity.EntryData;
import com.vcare.entity.EntryHistoryData;
import com.vcare.repository.CallCategoryRepository;
import com.vcare.repository.CallTypeRepository;
import com.vcare.repository.ClientRepository;
import com.vcare.repository.EmployeeRepository;
import com.vcare.repository.EntryDataRepository;
import com.vcare.repository.EntryHistoryDataRepository;

@Service
public class EntryDataService {

	@Autowired
	CallTypeRepository repository;
	@Autowired
	CallCategoryRepository catRepo;
	@Autowired
	ClientRepository clientRepo;
	@Autowired
	EmployeeRepository empRepo;
	@Autowired
	EntryDataRepository dataRepo;
	@Autowired
	EntryHistoryDataRepository historyDataRepo;

	static Integer jobId = 0;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<CallType> getCallType() {
		logger.info("Getting CallType Data");
		return repository.findByDeleted(false);

	}

	public List<CallCategory> getCallCategory() {
		logger.info("Getting getCallCategory Data");
		return catRepo.findByDeleted(false);

	}

	public List<Employee> getEmployee() {
		logger.info("Getting getEmployee Data");
		return empRepo.findByDeleted(false);

	}

	public List<Client> getClient() {
		logger.info("Getting getClient Data");
		return clientRepo.findClientByDeleted(false);

	}

	public Response getEntryBaseData() {
		try {
			List<CallType> callType = getCallType();
			List<CallCategory> callCategory = getCallCategory();
			List<Employee> employees = getEmployee();
			List<Client> clients = getClient();
			Response resposne = new Response();

			List<NameIdMap> callTypeList = callType.stream().map(emp -> {
				return new NameIdMap(emp.getId(), emp.getCallType());
			}).collect(Collectors.toList());
			List<NameIdMap> callCategoryList = callCategory.stream().map(cli -> {
				return new NameIdMap(cli.getId(), cli.getCallType());
			}).collect(Collectors.toList());

			List<NameIdMap> empList = employees.stream().map(emp -> {
				return new NameIdMap(emp.getId(), emp.getName());
			}).collect(Collectors.toList());
			List<NameIdMap> clientList = clients.stream().map(cli -> {
				return new NameIdMap(cli.getId(), cli.getName());
			}).collect(Collectors.toList());
			resposne.setCallTypeList(callTypeList);
			resposne.setCategoryList(callCategoryList);
			resposne.setClientList(clientList);
			resposne.setEmployeeList(empList);
			resposne.setResponseMessage("success");
			resposne.setHttpStatus(HttpStatus.OK);
			return resposne;
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Load Entry Data", e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}

	}

	public Response saveEntryData(EntryDataDTO entryData) {
		ObjectMapper obj = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			Response resposne = new Response();
			if (!checkEntries(entryData, resposne)) {
				logger.error("Failed to Save Entry Data");
				resposne.setHttpStatus(HttpStatus.NOT_FOUND);
				return resposne;
			}

			EntryData data = obj.convertValue(entryData, EntryData.class);
			if (entryData.getJobId() < 1) {
				if (jobId == 0) {
					EntryData topId = dataRepo.findTopByOrderByJobIdDesc();
					if (topId != null) {
						data.setJobId(topId.getJobId() + 1);
						jobId = topId.getJobId() + 1;
					} else {
						data.setJobId(1);
						++jobId;
					}
				} else {
					data.setJobId(++jobId);
				}
			}
			EntryData dataresp = dataRepo.save(data);
			resposne.setEntryData(dataresp);
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

	private boolean checkEntries(EntryDataDTO entryData, Response resposne) {
		if (!repository.findById(entryData.getCallTypeId()).isPresent()) {
			logger.error("Call Type ID is wrong:" + entryData.getCallTypeId());
			resposne.setResponseMessage("Call Type is not present");
			return false;
		}
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

		if (!catRepo.findById(entryData.getCategoryType()).isPresent()) {
			logger.error("Category Type ID is wrong:" + entryData.getCategoryType());
			resposne.setResponseMessage("Category Type is not present");
			return false;
		}
		return true;

	}

	public Response editEntryData(EntryDataDTO entryData) {
		try {
			savetoHistory(entryData);
		} catch (Exception e) {
			Response resposne = new Response();
			resposne.setResponseMessage("failure");
			logger.error("Failed to Save Entry history Data", e);
			resposne.setHttpStatus(HttpStatus.NOT_FOUND);
			return resposne;
		}
		return saveEntryData(entryData);
	}

	private void savetoHistory(EntryDataDTO entryData) {
		Optional<EntryData> dataOpt = dataRepo.findById(entryData.getId());
		if (dataOpt.isPresent()) {
			EntryData data = dataOpt.get();
			ObjectMapper obj = new ObjectMapper();
			EntryHistoryData historDta = obj.convertValue(data, EntryHistoryData.class);
			historDta.setUpdateDate(new Date());
			historDta.setEntryDataId(entryData.getId());
			historDta.setId(null);
			historyDataRepo.save(historDta);
			entryData.setJobId(data.getJobId());

		}

	}

	public Response getByIds(EntryDataSearch search, int page) {

		String empId = "";
		String clientId = "";
		Employee emp = empRepo.findByNameAndDeleted(search.getEmployeeName(), false);
		if (!StringUtils.isEmpty(search.getEmployeeName()) && emp == null) {
			Response response = new Response();
			response.setEntryDataList(new ArrayList<EntryDataDTO>());
			return response;
		}
		if (emp != null) {
			empId = emp.getId();
		}
		Client client = clientRepo.findByNameAndDeleted(search.getClientName(), false);
		if (!StringUtils.isEmpty(search.getClientName()) && client == null) {
			Response response = new Response();
			response.setEntryDataList(new ArrayList<EntryDataDTO>());
			return response;
		}
		if (client != null) {
			clientId = client.getId();
		}

		List<EntryData> resp = dataRepo.findEntryDataByProperties(empId, clientId, search.getWorkStatus(),search.getJobId(), page);
		final ObjectMapper obj = new ObjectMapper();
		List<EntryDataDTO> dataLIst = resp.stream().map(dt -> {
			String empName = search.getEmployeeName();
			String clientName = search.getClientName();
			if (StringUtils.isEmpty(empName) || StringUtils.isEmpty(empName.trim())) {
				empName = empRepo.findById(dt.getEmployeeId()).get().getName();
			}
			if (StringUtils.isEmpty(clientName) || StringUtils.isEmpty(clientName.trim())) {
				clientName = clientRepo.findById(dt.getClientId()).get().getName();
			}
			String callTYpe = repository.findById(dt.getCallTypeId()).get().getCallType();
			String catTYpe = catRepo.findById(dt.getCategoryType()).get().getCallType();
			EntryDataDTO newDt = obj.convertValue(dt, EntryDataDTO.class);
			newDt.setEmpName(empName);
			newDt.setClientName(clientName);
			newDt.setCallTypeName(callTYpe);
			newDt.setCategoryTypeName(catTYpe);
			return newDt;
		}).collect(Collectors.toList());
		Response response = new Response();
		response.setEntryDataList(dataLIst);
		return response;
	}

	public Response getAllByPage(int page) {
		long count = 0;
		if (page == 1) {
			long countDt = dataRepo.count();
			if (countDt < 10) {
				count = 1;
			} else {
				count = countDt / 10 + countDt % 10;
			}
		}

		List<EntryData> resp = dataRepo.findEntryDataPage(page);
		final ObjectMapper obj = new ObjectMapper();
		List<EntryDataDTO> dataLIst = resp.stream().map(dt -> {
			String empName = empRepo.findById(dt.getEmployeeId()).get().getName();
			String clientName = clientRepo.findById(dt.getClientId()).get().getName();
			String callTYpe = repository.findById(dt.getCallTypeId()).get().getCallType();
			String catTYpe = catRepo.findById(dt.getCategoryType()).get().getCallType();
			EntryDataDTO newDt = obj.convertValue(dt, EntryDataDTO.class);
			newDt.setEmpName(empName);
			newDt.setClientName(clientName);
			newDt.setCallTypeName(callTYpe);
			newDt.setCategoryTypeName(catTYpe);
			return newDt;
		}).collect(Collectors.toList());
		Response response = new Response();
		response.setEntryDataList(dataLIst);
		if (count != 0) {
			response.setPageCount(count);
		}
		return response;
	}

}
