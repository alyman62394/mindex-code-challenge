package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    private String employeeUrl;
    private String employeeIdUrl;

    @Autowired
    private EmployeeController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
    }

    @Test
    public void testReadEmployee() {
        Employee employee = restTemplate.getForEntity(employeeIdUrl, Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        assertNotNull(employee);
        assertEquals("16a596ae-edd3-4847-99fe-c4518e82c86f", employee.getEmployeeId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());
        assertNotNull(employee.getDirectReports());
        assertEquals(2, employee.getDirectReports().size());
        assertEquals("b7839309-3348-463b-a7e3-5de1c168beb3", employee.getDirectReports().get(0).getEmployeeId());
        assertEquals("03aa1462-ffa9-4978-901b-7c001562cf6f", employee.getDirectReports().get(1).getEmployeeId());
    }

    @Test
    public void testCreateEmployeeAndUpdate() {
        Employee employee = new Employee();
        employee.setFirstName("Amelia");
        employee.setLastName("McMullen");
        employee.setDepartment("Engineering");
        employee.setPosition("Developer");

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, employee, Employee.class).getBody();

        assertNotNull(createdEmployee);
        assertNotNull(createdEmployee.getEmployeeId());
        assertEquals(employee.getFirstName(), createdEmployee.getFirstName());
        assertEquals(employee.getLastName(), createdEmployee.getLastName());
        assertEquals(employee.getDepartment(), createdEmployee.getDepartment());
        assertEquals(employee.getPosition(), createdEmployee.getPosition());

        employee.setFirstName("This");
        employee.setLastName("Is");
        employee.setDepartment("A");
        employee.setPosition("Test");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Employee>(employee, headers),
                        Employee.class,
                        createdEmployee.getEmployeeId()).getBody();
        assertEquals("This", updatedEmployee.getFirstName());
        assertEquals("Is", updatedEmployee.getLastName());
        assertEquals("A", updatedEmployee.getDepartment());
        assertEquals("Test", updatedEmployee.getPosition());
    }

    @Test
    public void testUpdateEmployee_InvalidId() {
        Employee employee = restTemplate.getForEntity(employeeIdUrl, Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        assertNotNull(employee);
        employee.setLastName("McMullen");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity resp = restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Employee>(employee, headers),
                        Employee.class,
                        "16a596ae-edd3-4847-99fe-THISISATEST");
        assertNotNull(resp);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
    }
}
