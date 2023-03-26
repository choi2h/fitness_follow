package com.ffs.controller;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.dao.Employee;
import com.ffs.dto.EmployeeResult;
import com.ffs.dto.RegisterEmployeeRequest;
import com.ffs.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Object> registerNewEmployee(@RequestBody @Valid RegisterEmployeeRequest request) {
        Long employeeId = employeeService.registerNewEmployee(request);
        EmployeeResult response = EmployeeResult.builder().id(employeeId).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        List<Employee> employeeList;

        try {
            employeeList = employeeService.getAllEmployee();
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        EmployeeResult response = EmployeeResult.builder().employeeList(employeeList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBranchById(@PathVariable Long id) {
        Employee employee;

        try {
            employee = employeeService.getEmployeeById(id);
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        EmployeeResult response = EmployeeResult.builder().employee(employee).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
