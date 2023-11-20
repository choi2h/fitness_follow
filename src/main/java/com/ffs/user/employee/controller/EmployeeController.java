package com.ffs.user.employee.controller;

import com.ffs.auth.PrincipalDetails;
import com.ffs.user.employee.dto.*;
import com.ffs.user.employee.application.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        List<EmployeeInfo> employeeInfoList = employeeService.getAllEmployee();
        EmployeeResult response = EmployeeResult.builder().employeeList(employeeInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long id = principalDetails.getId();
        EmployeeInfo employeeInfo = employeeService.getEmployeeById(id);
        EmployeeResult response = EmployeeResult.builder().employee(employeeInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_TRAINER')")
    public ResponseEntity<Object> getEmployeeInfoById
            (@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("id") Long id) {
        Long branchId = principalDetails.getAuthUser().getBranchId();
        EmployeeInfo employeeInfo = employeeService.getEmployeeByBranchIdAndEmployeeId(branchId, id);
        EmployeeResult response = EmployeeResult.builder().employee(employeeInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getEmployeesByBranchId(@PathVariable Long id) {
        List<EmployeeInfo> employeeInfoList = employeeService.getEmployeeByBranchId(id);
        EmployeeResult response = EmployeeResult.builder().employeeList(employeeInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/status")
    public ResponseEntity<Object> updateEmployeeStatus
            (@AuthenticationPrincipal PrincipalDetails principalDetails, UpdateEmployeeStatusRequest request) {
        Long id = principalDetails.getId();
        EmployeeInfo employeeInfo = employeeService.updateEmployeeStatus(id, request);
        EmployeeResult response = EmployeeResult.builder().employee(employeeInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> updateEmployeeStatus
            (@AuthenticationPrincipal PrincipalDetails principalDetails, UpdateEmployeeRequest request) {
        Long id = principalDetails.getId();
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
