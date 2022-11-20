package com.vcare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vcare.entity.EntryHistoryData;

public interface EntryHistoryDataRepository extends MongoRepository<EntryHistoryData, String> {


}