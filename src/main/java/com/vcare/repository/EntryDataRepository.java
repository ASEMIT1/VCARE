package com.vcare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.vcare.entity.EntryData;

public interface EntryDataRepository extends MongoRepository<EntryData, String>,EntryDataCustomRepository {

	@Query("{ ?0 : ?1 }")
	List<EntryData> findByDynamicField(String field, Object value);
	@Query("{ 'employeeId': ?0 , 'clientId': ?1  }")
	List<EntryData> findAllEntryDataByEmployeeIdAndClientIdAndWorkStatus(String employeeId, String clientId,String workStatus);
	List<EntryData> findAllByEmployeeId(String employeeId);
	List<EntryData> findAllByEmployeeIdAndClientId(String employeeId, String clientId);
	EntryData findTopByOrderByJobIdDesc();
}