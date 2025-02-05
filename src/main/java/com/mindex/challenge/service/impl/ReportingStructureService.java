package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

import java.util.List;

public class ReportingStructureService {
    private final EmployeeService employeeService;

    public ReportingStructureService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public ReportingStructure getReportingStructure (String id) {
        Employee emp = employeeService.read(id);
        int numOfReports = getNumberOfReports(emp);
        return new ReportingStructure(emp, numOfReports);
    }

    private int getNumberOfReports(Employee employee) {
        List<Employee> directReports = employee.getDirectReports();
        if (null == directReports || directReports.isEmpty()) {
            return 0;
        }
        int reports = employee.getDirectReports().size();
        for(Employee emp: directReports) {
            emp = employeeService.read(emp.getEmployeeId());
            reports += getNumberOfReports(emp);
        }
        return reports;
    }
}
