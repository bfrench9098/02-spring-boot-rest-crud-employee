package com.edgesoftusa.rest;

import com.edgesoftusa.entity.Employee;
import com.edgesoftusa.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;

    // quick and dirty: inject employee dao
    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    // expose "/employees" and return list of employees
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    // expose "/employees/{employeeId}" and return list of employees
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);
        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        return theEmployee;
    }

    // add mapping for POST /employees - add new employee
    // receive inbound json payload
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody String theEmployeeJson) {
        ObjectMapper mapper = new ObjectMapper();
        Employee theEmployee = null;
        try {
            theEmployee = mapper.readValue(theEmployeeJson, Employee.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        theEmployee.setId(0);
        Employee dbEmployee = employeeService.save(theEmployee);

        return dbEmployee;
    }

    // add mapping for PUT /employees = update existing
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody String theEmployeeJson) {
        ObjectMapper mapper = new ObjectMapper();
        Employee theEmployee = null;
        try {
            theEmployee = mapper.readValue(theEmployeeJson, Employee.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Employee dbEmployee = employeeService.save(theEmployee);

        return dbEmployee;
    }

    // add mapping for DELETE /employees/{employeeId}
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);

        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        employeeService.deleteById(tempEmployee.getId());

        return "Employee with following Id deleted: " + employeeId;
    }
}
