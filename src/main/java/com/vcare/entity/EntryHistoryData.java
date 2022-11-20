package com.vcare.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryHistoryData {

	@Id
	String id;
	String entryDataId;
	String callTypeId;
	String categoryType;
	String employeeId;
	String clientId;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	Date serviceDate;
	String issueDetail;
	String workStatus;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")Date completionDate;
	String bookChallanNo;
	String margChallanNo;
	String billNum;
	double totalPayment;
	double balancePayment;
	double paymentRecieved;
	String paymentStatus;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	Date paymentReminder;
	Date updateDate;
	Integer jobId;
	
	
}
