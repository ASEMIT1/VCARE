package com.vcare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vcare.entity.CallCategory;
import com.vcare.entity.CallType;

public interface CallCategoryRepository extends MongoRepository<CallCategory, String> {

  
  public List<CallCategory> findAll();
  
  public List<CallCategory> findByDeleted(boolean delete);
  public List<CallCategory> findByCallType(String callType);
}