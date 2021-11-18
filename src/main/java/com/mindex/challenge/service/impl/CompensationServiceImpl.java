/*
 * This file was created by Rahul Golhar
 * for solving the task 2 of the
 * Mindex Java coding challenge
 */

package com.mindex.challenge.service.impl;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService{

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;
    /*
     * This function is used to
     * insert a new entry to the compensation repository.
     */
    @Override
    public Compensation create(Compensation compensation) {

        // extract the employeeId
        String employeeId = compensation.getEmployee().getEmployeeId();

        LOG.debug("Creating compensation for employee with employeeID [{}]", employeeId);

        // check whether the employee with given ID exists in the employee repository
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            System.out.println("Employee not found");
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        Compensation prev_compensation = compensationRepository.findByEmployee_EmployeeId(employeeId);
        if (prev_compensation != null) {
            System.out.println("Compensation entry found");
            throw new RuntimeException("Existing entry found for employeeId: " + employeeId);
        }

        // update the values of the passed object and
        // add it to the compensation repository
        compensation.setEmployee(employee);
        compensation.setEffectiveDate(LocalDate.now());

        compensationRepository.insert(compensation);

        System.out.println("compensation created");

        return compensation;
    }

    /*
     * This method is used to read an entry for the given employee
     * from the compensation repository.
     */
    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Reading compensation for employee with id [{}]", employeeId);


        Compensation compensation = compensationRepository.findByEmployee_EmployeeId(employeeId);

        // check whether there is an entry for the given employee
        // in the compensation repository
        if (compensation == null) {
            throw new RuntimeException("Compensation not created for employee with id: " + employeeId);
        }

        return compensation;
    }
}
