package com.mindex.challenge.context;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;

import java.util.Date;

public class CompensationContext {
    private final EmployeeService employeeService;

    public CompensationContext(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Update the compensation for an employee
     *
     * @param employeeId id of employee
     * @param salary salary to update for employee
     * @param effectiveDate effective date for employee
     * @return employee with updated compensation
     */
    public Employee setCompensation(String employeeId, int salary, Date effectiveDate) {
        Employee employee = employeeService.read(employeeId);
        employee.setCompensation(salary, effectiveDate);
        return employeeService.update(employee);
    }

    /**
     * Update the compensation for an employee
     *
     * @param employeeId id of employee
     * @param compensation compensation object containing date and salary
     * @return employee with updated compensation
     */
    public Employee setCompensation(String employeeId, Compensation compensation) {
        Employee employee = employeeService.read(employeeId);
        employee.setCompensation(compensation);
        return employeeService.update(employee);
    }

    public Compensation getCompensation(String employeeId) {
        Employee employee = employeeService.read(employeeId);
        return employee.getCompensation();
    }
}
