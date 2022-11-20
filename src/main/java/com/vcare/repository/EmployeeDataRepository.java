package com.vcare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vcare.entity.EmployeeEntryData;

public interface EmployeeDataRepository extends MongoRepository<EmployeeEntryData, String> {

	public List<EmployeeEntryData> findByJobId(int jobId);

}