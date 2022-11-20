package com.vcare.repository;

import java.util.List;

import com.vcare.entity.EmployeeEntryDataSearch;
import com.vcare.entity.EntryData;

public interface EntryDataCustomRepository {
	public List<EntryData> findEntryDataByProperties(String employeeId, String clientId, String workStatus,int jobId, int page);
	
	public List<EmployeeEntryDataSearch> findEntryDataForEmployeeByProperties(String employeeId, String workStatus);


	List<EntryData> findEntryDataPage(int page);
}