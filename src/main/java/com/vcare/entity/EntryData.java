package com.vcare.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = "entryData")
public class EntryData {

	@Id
	@Indexed
	String id;
	@Indexed
	Integer jobId;
	String callTypeId;
	String categoryType;
	@Indexed
	String employeeId;
	@Indexed
	String clientId;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	Date serviceDate;
	String issueDetail;
	@Indexed
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
	
	
}
