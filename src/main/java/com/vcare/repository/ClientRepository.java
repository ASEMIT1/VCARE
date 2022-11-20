package com.vcare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vcare.entity.Client;

public interface ClientRepository extends MongoRepository<Client, String> {

  public Client findByName(String name);
  
  public Client findByNameAndDeleted(String name,boolean deleted);
  
  public List<Client> findAll();
  
  public List<Client> findClientByDeleted(boolean deleted);

}