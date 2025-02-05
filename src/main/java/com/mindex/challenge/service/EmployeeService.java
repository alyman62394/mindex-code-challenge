package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);
}
