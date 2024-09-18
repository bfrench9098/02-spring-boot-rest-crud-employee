package com.edgesoftusa.service;

import com.edgesoftusa.entity.Employee;
import jakarta.transaction.Transactional;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(int theId);
    Employee save(Employee theEmployee);
    void deleteById(int theId);
}

