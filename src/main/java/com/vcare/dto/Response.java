package com.vcare.dto;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vcare.entity.CallCategory;
import com.vcare.entity.CallType;
import com.vcare.entity.Client;
import com.vcare.entity.Employee;
import com.vcare.entity.EmployeeEntryData;
import com.vcare.entity.EmployeeEntryDataSearch;
import com.vcare.entity.EntryData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Response {

	private String responseMessage;
	private HttpStatus httpStatus;
	private Client client;
	private Employee employee;
	private CallType callType;
	private List<CallType> callTypes;

	private CallCategory catType;
	private List<CallCategory> catTypes;
	private List<NameIdMap> clientList;
	private List<NameIdMap> employeeList;
	private List<NameIdMap> callTypeList;
	private List<NameIdMap> categoryList;
	private String token;
	private Boolean createUser;
	private String role;
	private Boolean editEnabled;
	private EntryData entryData;
	private List<EntryDataDTO> entryDataList;
	private Long pageCount;
	private Map<Integer, EmployeeEntryDataSearch> employeeDataList;
	private EmployeeEntryData employeeEntryData;
	private List<EmployeeEntryDataForJobId> dataForJobId;

}
