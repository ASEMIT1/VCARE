package com.vcare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vcare.entity.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

  public Employee findByName(String name);
  
  public Employee findByNameAndDeleted(String name, boolean delete);
  public List<Employee> findByDeleted(boolean delete);
  
  public List<Employee> findAll();

}