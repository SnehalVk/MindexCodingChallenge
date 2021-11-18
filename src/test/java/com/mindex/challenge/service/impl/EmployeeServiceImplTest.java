package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;
    private String employeeReportingStructUrl;
    private ReportingStructure reportingStruct;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        employeeReportingStructUrl = "http://localhost:" + port + "reportingStructure/employee/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);


        // Read checks
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);

        // Update checks
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Employee>(readEmployee, headers),
                        Employee.class,
                        readEmployee.getEmployeeId()).getBody();

        assertEmployeeEquivalence(readEmployee, updatedEmployee);

    // Validating reporting structure for John Lennon.
    reportingStruct = restTemplate.getForEntity(employeeReportingStructUrl,
            ReportingStructure.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
    assertNotNull(reportingStruct);
    assertEquals(4, reportingStruct.getNumberOfReports());

    // Validating reporting structure for Ringo Starr.
        reportingStruct = restTemplate.getForEntity(employeeReportingStructUrl,
            ReportingStructure.class, "03aa1462-ffa9-4978-901b-7c001562cf6f").getBody();
    assertNotNull(reportingStruct);
    assertEquals(2, reportingStruct.getNumberOfReports());

    // Validating reporting structure for Pete Best.
        reportingStruct = restTemplate.getForEntity(employeeReportingStructUrl,
            ReportingStructure.class, "62c1084e-6e34-4630-93fd-9153afb65309").getBody();
    assertNotNull(reportingStruct);
    assertEquals(0, reportingStruct.getNumberOfReports());

    // Validating reporting structure for George Harrison.
        reportingStruct = restTemplate.getForEntity(employeeReportingStructUrl,
            ReportingStructure.class, "c0c2293d-16bd-4603-8e08-638a9d18b22c").getBody();
    assertNotNull(reportingStruct);
    assertEquals(0, reportingStruct.getNumberOfReports());

    // Validating reporting structure for Paul McCartney.
        reportingStruct = restTemplate.getForEntity(employeeReportingStructUrl,
            ReportingStructure.class, "b7839309-3348-463b-a7e3-5de1c168beb3").getBody();
    assertNotNull(reportingStruct);
    assertEquals(0, reportingStruct.getNumberOfReports());

    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
