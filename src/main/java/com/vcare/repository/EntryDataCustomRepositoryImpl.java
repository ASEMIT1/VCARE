package com.vcare.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.vcare.entity.EmployeeEntryDataSearch;
import com.vcare.entity.EntryData;

@Repository
public class EntryDataCustomRepositoryImpl implements EntryDataCustomRepository {
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<EntryData> findEntryDataByProperties(String employeeId, String clientId, String workStatus,int jobId, int page) {
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		if (!StringUtils.isEmpty(employeeId))
			criteria.add(Criteria.where("employeeId").is(employeeId));
		if (!StringUtils.isEmpty(clientId))
			criteria.add(Criteria.where("clientId").is(clientId));
		if (!StringUtils.isEmpty(workStatus))
			criteria.add(Criteria.where("workStatus").is(workStatus));
		if (jobId!=0)
			criteria.add(Criteria.where("jobId").is(jobId));
		org.springframework.data.domain.Pageable pageable = PageRequest.of(page-1, 10);
		if (!criteria.isEmpty())
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]))).with(pageable);
		
		return mongoTemplate.find(query, EntryData.class);
	}

	@Override
	public List<EntryData> findEntryDataPage(int page) {

		org.springframework.data.domain.Pageable pageable = PageRequest.of(page-1, 10);
		Query query = new Query().with(pageable);
		List<EntryData> list = mongoTemplate.find(query, EntryData.class);
		return list;
	}

	@Override
	public List<EmployeeEntryDataSearch> findEntryDataForEmployeeByProperties(String employeeId, String workStatus) {
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		if (!StringUtils.isEmpty(employeeId))
			criteria.add(Criteria.where("employeeId").is(employeeId));
		if (!StringUtils.isEmpty(workStatus))
			criteria.add(Criteria.where("workStatus").ne(workStatus));

		if (!criteria.isEmpty())
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		return mongoTemplate.find(query, EmployeeEntryDataSearch.class);
	}

}