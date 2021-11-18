/*
 * This file was created by Snehal Kulkarni for solving
 * the task 2.
 */

package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /*
     * This function is used to
     * create a new compensation entry in the system.
     */
    @PostMapping("/compensation/create")
    public Compensation create(@RequestBody Compensation compensation) throws Exception {
        LOG.debug("Received Compensation create request for [{}]",
                compensation.getEmployee().getEmployeeId());

        compensationService.create(compensation);

        return compensation;
    }

    /*
     * This function is used to read the compensation of
     * the employee with given employee ID.
     */
    @GetMapping("/compensation/read/{employeeId}")
    public Compensation read(@PathVariable String employeeId) throws Exception {
        LOG.debug("Received Compensation read request for id [{}]", employeeId);

        return compensationService.read(employeeId);
    }

}
