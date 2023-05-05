package com.ffs.web.employee.controller;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.employee.EmployeeInfo;
import com.ffs.web.employee.EmployeeResult;
import com.ffs.web.employee.request.RegisterEmployeeRequest;
import com.ffs.domain.employee.service.EmployeeService;
import com.ffs.web.employee.request.UpdateEmployeeStatusRequest;
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
        EmployeeInfo employee = employeeService.registerNewEmployee(request);
        EmployeeResult response = EmployeeResult.builder().employee(employee).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        List<EmployeeInfo> employeeInfoList;

        try {
            employeeInfoList = employeeService.getAllEmployee();
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        EmployeeResult response = EmployeeResult.builder().employeeList(employeeInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        EmployeeInfo employeeInfo;

        try {
            employeeInfo = employeeService.getEmployeeById(id);
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        EmployeeResult response = EmployeeResult.builder().employee(employeeInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getEmployeeByBranchId(@PathVariable Long id) {
        List<EmployeeInfo> employeeInfoList;

        try{
            employeeInfoList = employeeService.getEmployeeByBranchId(id);
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        EmployeeResult response = EmployeeResult.builder().employeeList(employeeInfoList).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Object> updateEmployeeStatus(@PathVariable Long id, UpdateEmployeeStatusRequest request) {
        EmployeeInfo employeeInfo;

        try {
            employeeInfo = employeeService.updateEmployeeStatus(id, request);
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        EmployeeResult response = EmployeeResult.builder().employee(employeeInfo).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
