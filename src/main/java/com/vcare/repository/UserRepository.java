package com.vcare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vcare.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

	public User findByName(String name);

}