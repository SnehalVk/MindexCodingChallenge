/*
 * This file was created by Snehal Kulkarni for solving
 * the task 2.
 */

package com.mindex.challenge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mindex.challenge.data.Compensation;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
       Compensation findByEmployee_EmployeeId(String employeeId);
}