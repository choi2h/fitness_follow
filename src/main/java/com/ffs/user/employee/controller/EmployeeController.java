package com.ffs.user.employee.controller;

import com.ffs.user.employee.dto.*;
import com.ffs.user.employee.application.EmployeeService;
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

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        List<EmployeeInfo> employeeInfoList = employeeService.getAllEmployee();
        EmployeeResult response = EmployeeResult.builder().employeeList(employeeInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        EmployeeInfo employeeInfo = employeeService.getEmployeeById(id);
        EmployeeResult response = EmployeeResult.builder().employee(employeeInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getEmployeeByBranchId(@PathVariable Long id) {
        List<EmployeeInfo> employeeInfoList = employeeService.getEmployeeByBranchId(id);
        EmployeeResult response = EmployeeResult.builder().employeeList(employeeInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Object> updateEmployeeStatus(@PathVariable Long id, UpdateEmployeeStatusRequest request) {
        EmployeeInfo employeeInfo = employeeService.updateEmployeeStatus(id, request);
        EmployeeResult response = EmployeeResult.builder().employee(employeeInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployeeStatus(@PathVariable Long id, UpdateEmployeeRequest request) {
        EmployeeInfo employeeInfo = employeeService.updateEmployeeInfo(id, request);
        EmployeeResult response = EmployeeResult.builder().employee(employeeInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<Object> registerNewEmployee(@RequestBody @Valid RegisterEmployeeRequest request) {
        EmployeeInfo employee = employeeService.registerNewEmployee(request);
        EmployeeResult response = EmployeeResult.builder().employee(employee).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
