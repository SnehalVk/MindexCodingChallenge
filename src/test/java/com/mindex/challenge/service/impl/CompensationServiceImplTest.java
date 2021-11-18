/*
 * This file was created by Snehal Kulkarni for solving
 * the task 2.
 */

package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    // Variables Endpoints are
    private String employeeIdUrl;
    private String compensationCreateUrl;
    private String compensationReadUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        // URL to read an employee from database
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";

        // URL to create a compensation and save it in the database
        compensationCreateUrl = "http://localhost:" + port + "/compensation/create";

        // URL to read a compensation from database
        compensationReadUrl = "http://localhost:" + port + "/compensation/read/{employeeId}";
    }

    @Test
    public void testCreateReadUpdate() {

        // Retrieve an exiting employee object from database
        Employee employee = restTemplate.getForEntity(employeeIdUrl, Employee.class,
                "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();

        // Validate created compensation object.
        // Compensation object created to test.
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(employee);
        testCompensation.setSalary(100000);
        testCompensation.setEffectiveDate(LocalDate.now());;

        // Compensation object created by the system
        Compensation createdCompensation = restTemplate.postForEntity(compensationCreateUrl,
                testCompensation, Compensation.class).getBody();
        assertNotNull(createdCompensation.getEffectiveDate());

        // Validate the test and created objects by comparing them.
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Validate reading of a compensation object.
        // compensation object read by the system
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class,
                testCompensation.getEmployee().getEmployeeId()).getBody();

        assertNotNull(readCompensation);

        // Validate the test and created objects by comparing them.
        assertCompensationEquivalence(testCompensation, readCompensation);

    }

    /*
     * This function compares two compensation objects
     * for equivalence of their values.
     */
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

    /*
     * This function compares two employee objects
     * for equivalence of their values.
     */
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
