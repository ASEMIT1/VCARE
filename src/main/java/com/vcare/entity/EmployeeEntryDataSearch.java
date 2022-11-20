package com.vcare.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "entryData")
public class EmployeeEntryDataSearch {

	String id;
	Integer jobId;
	String callTypeId;
	String categoryType;
	String employeeId;
	String clientId;
	String employeeName;
	String clientName;
	String callType;
	String category;
	
	
	
}
