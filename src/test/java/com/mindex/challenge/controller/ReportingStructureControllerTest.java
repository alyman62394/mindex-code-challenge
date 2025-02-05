package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureControllerTest {

    private String employeeUrl;
    private String reportingStructureUrl;

    @Autowired
    private ReportingStructureController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @After
    public void reset() {

    }

    @Test
    public void testFullTreeStructure() {
        ReportingStructure structure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        assertNotNull(structure);
        assertNotNull(structure.getEmployee());
        assertEquals("16a596ae-edd3-4847-99fe-c4518e82c86f", structure.getEmployee().getEmployeeId());
        assertEquals("Lennon", structure.getEmployee().getLastName());
        assertEquals("Development Manager", structure.getEmployee().getPosition());
        assertEquals("Engineering", structure.getEmployee().getDepartment());
        assertNotNull(structure.getEmployee().getDirectReports());
        assertEquals(2, structure.getEmployee().getDirectReports().size());
        assertNotNull(structure.getEmployee().getDirectReports().get(0).getFirstName());
        assertNotNull(structure.getEmployee().getDirectReports().get(0).getLastName());
        assertNull(structure.getEmployee().getDirectReports().get(0).getDirectReports());
        assertNotNull(structure.getEmployee().getDirectReports().get(1).getFirstName());
        assertNotNull(structure.getEmployee().getDirectReports().get(1).getLastName());
        assertNotNull(structure.getEmployee().getDirectReports().get(1).getDirectReports());
        assertEquals(2, structure.getEmployee().getDirectReports().get(1).getDirectReports().size());
        assertNotNull(structure.getEmployee().getDirectReports().get(1).getDirectReports().get(0).getFirstName());
        assertNotNull(structure.getEmployee().getDirectReports().get(1).getDirectReports().get(0).getLastName());
        assertNull(structure.getEmployee().getDirectReports().get(1).getDirectReports().get(0).getDirectReports());
        assertNotNull(structure.getEmployee().getDirectReports().get(1).getDirectReports().get(1).getFirstName());
        assertNotNull(structure.getEmployee().getDirectReports().get(1).getDirectReports().get(1).getLastName());
        assertNull(structure.getEmployee().getDirectReports().get(1).getDirectReports().get(1).getDirectReports());
        assertEquals(4, structure.getNumberOfReports());
    }

    @Test
    public void testOneLevelTreeStructure() {
        ReportingStructure structure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "03aa1462-ffa9-4978-901b-7c001562cf6f").getBody();
        assertNotNull(structure);
        assertNotNull(structure.getEmployee());
        assertEquals("03aa1462-ffa9-4978-901b-7c001562cf6f", structure.getEmployee().getEmployeeId());
        assertEquals("Starr", structure.getEmployee().getLastName());
        assertEquals("Developer V", structure.getEmployee().getPosition());
        assertEquals("Engineering", structure.getEmployee().getDepartment());
        assertNotNull(structure.getEmployee().getDirectReports());
        assertEquals(2, structure.getEmployee().getDirectReports().size());
        assertEquals(2, structure.getNumberOfReports());
    }
    @Test
    public void testBottomLevelTreeStructure() {
        ReportingStructure structure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "b7839309-3348-463b-a7e3-5de1c168beb3").getBody();
        assertNotNull(structure);
        assertNotNull(structure.getEmployee());
        assertEquals("b7839309-3348-463b-a7e3-5de1c168beb3", structure.getEmployee().getEmployeeId());
        assertEquals("McCartney", structure.getEmployee().getLastName());
        assertEquals("Developer I", structure.getEmployee().getPosition());
        assertEquals("Engineering", structure.getEmployee().getDepartment());
        assertNull(structure.getEmployee().getDirectReports());
        assertEquals(0, structure.getNumberOfReports());
    }
}
