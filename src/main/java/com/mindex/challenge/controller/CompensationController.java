package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.context.CompensationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);
        CompensationContext compensationContext = new CompensationContext(employeeService);
        return compensationContext.getCompensation(id);
    }

    @PutMapping("/compensation/{id}")
    public Compensation update(@PathVariable String id, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation request for id [{}] and compensation [{}]", id, compensation);

        CompensationContext compensationContext = new CompensationContext(employeeService);
        Employee employee = compensationContext.setCompensation(id, compensation);
        return employee.getCompensation();
    }
}
