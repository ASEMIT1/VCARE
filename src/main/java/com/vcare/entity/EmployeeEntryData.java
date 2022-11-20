package com.vcare.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeEntryData implements Serializable {
	@Id
	String id;
	@Indexed
	String employeeId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	Date updateDate;
	String bookChallanNo;
	String itemUsed;

	String itemReturned;
	@Indexed
	String clientId;
	@Indexed
	int jobId;
	String workStatus;
	String engineerStatus;
	String paymentUpdate;

}
