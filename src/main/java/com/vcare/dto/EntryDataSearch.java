package com.vcare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryDataSearch {
	String employeeName;
	String clientName;
	String workStatus;
	int jobId;

}
