package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
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

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationControllerTest {

    private String employeeUrl;
    private String compensationUrl;

    @Autowired
    private ReportingStructureController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee/{id}";
        compensationUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testSettingCompensationAndGettingAgain() {
        Employee employee = restTemplate.getForEntity(employeeUrl, Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        Date now = new Date();
        employee.setCompensation(123000, now);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation comp =
                restTemplate.exchange(compensationUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Compensation>(employee.getCompensation(), headers),
                        Compensation.class,
                        employee.getEmployeeId()).getBody();
        assertNotNull(comp);
        assertEquals(now, comp.getEffectiveDate());
        assertEquals(123000, comp.getSalary());

        Employee fetchedEmployee = restTemplate.getForEntity(employeeUrl, Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        assertNotNull(fetchedEmployee);
        assertNotNull(fetchedEmployee.getCompensation());
        assertEquals(now, fetchedEmployee.getCompensation().getEffectiveDate());
        assertEquals(123000, fetchedEmployee.getCompensation().getSalary());
    }
}
