/*
 * This file was created by Rahul Golhar to solve
 * the task 2 in Mindex Java coding challenge.
 */

package com.mindex.challenge.service;

import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Compensation;

@Service
public interface CompensationService {

    Compensation create(Compensation compensation) throws Exception;
    Compensation read(String employeeId) throws Exception;
}
