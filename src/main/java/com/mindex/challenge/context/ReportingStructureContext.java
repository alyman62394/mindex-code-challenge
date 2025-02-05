package com.mindex.challenge.context;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

public class ReportingStructureContext {
    private final EmployeeService employeeService;

    public ReportingStructureContext(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public ReportingStructure getReportingStructure (String id) {
        Employee emp = employeeService.read(id);
        int numOfReports = getNumberOfReports(emp);
        return new ReportingStructure(emp, numOfReports);
    }

    /**
     * Recursive function to dig down to the bottom of the employee list and build up reporting employees.
     * Resets the direct reports list and adds the full employee object after fetching it.
     *
     * @param employee employee object to check the direct reports underneath it.
     * @return number of reports
     */
    private int getNumberOfReports(Employee employee) {
        List<Employee> directReports = employee.getDirectReports();
        if (null == directReports || directReports.isEmpty()) {
            return 0;
        }
        int reports = employee.getDirectReports().size();
        employee.setDirectReports(new ArrayList<>());
        for(Employee emp: directReports) {
            emp = employeeService.read(emp.getEmployeeId());
            reports += getNumberOfReports(emp);
            employee.addDirectReport(emp);
        }
        return reports;
    }
}
