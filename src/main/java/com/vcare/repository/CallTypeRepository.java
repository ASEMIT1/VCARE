package com.vcare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vcare.entity.CallType;

public interface CallTypeRepository extends MongoRepository<CallType, String> {

  
  public List<CallType> findAll();
  public List<CallType> findByDeleted(boolean delete);
  public List<CallType> findByCallType(String callType);
  

}