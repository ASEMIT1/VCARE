package com.vcare.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class EmployeeEntryDataDTO implements Serializable {

	@NotNull
	@NotEmpty
	String employeeId;
	@NotNull
	@NotEmpty
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	Date updateDate;
	@NotNull
	@NotEmpty
	String bookChallanNo;
	@NotNull
	@NotEmpty
	String itemUsed;

	String itemReturned;
	@NotNull
	@NotEmpty
	String clientId;
	@NotNull
	@NotEmpty
	int jobId;
	String workStatus;
	String engineerStatus;
	String paymentUpdate;

}
