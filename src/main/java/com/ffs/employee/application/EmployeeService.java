package com.ffs.employee.application;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.branch.domain.Branch;
import com.ffs.employee.domain.EmployeeStatus;
import com.ffs.employee.dto.EmployeeInfo;
import com.ffs.employee.domain.Employee;
import com.ffs.employee.dto.RegisterEmployeeRequest;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.employee.domain.repository.EmployeeRepository;
import com.ffs.branch.BranchResultCode;
import com.ffs.employee.EmployeeResultCode;
import com.ffs.employee.dto.UpdateEmployeeRequest;
import com.ffs.employee.dto.UpdateEmployeeStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeInfoMapper employeeMapperService;

    public EmployeeInfo registerNewEmployee(RegisterEmployeeRequest request) {
        log.debug("Register new Employee start. name={}", request.getName());

        Long branchId = request.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch for register employee. id={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        Employee employee = makeNewEmployee(request, branch);
        employee = employeeRepository.save(employee);

        log.info("Success to register employee. id={}, name={}", employee.getId(), employee.getName());
        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    public List<EmployeeInfo> getAllEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();

        if(employeeList.isEmpty()) {
            log.debug("Not exist employee anyone.");
            throw new ServiceResultCodeException(EmployeeResultCode.NO_REGISTERED_EMPLOYEE);
        }

        log.info("Found all employee. count={}", employeeList.size());
        return employeeMapperService.employeeListToEmployeeInfoList(employeeList);
    }

    public EmployeeInfo getEmployeeById(Long id) {
        log.debug("Search employee by id. id={}", id);

        Employee employee = findEmployeeById(id);
        log.debug("Found employee by id. id={}, name={}", id, employee.getName());

        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    public List<EmployeeInfo> getEmployeeByBranchId(Long branchId) {
        log.debug("Search employee list by branch id. branchId={}", branchId);

        List<Employee> employeeList = employeeRepository.findAllByBranchId(branchId);
        if(employeeList.isEmpty()) {
            log.debug("Not exist employee by branch id. branchId={}", branchId);
            throw new ServiceResultCodeException(EmployeeResultCode.NOT_EXIST_EMPLOYEE_FOR_BRANCH, branchId);
        }

        log.debug("Found employee list by branch id. count={}", employeeList.size());
        return employeeMapperService.employeeListToEmployeeInfoList(employeeList);
    }

    public EmployeeInfo updateEmployeeInfo(Long employeeId, UpdateEmployeeRequest request) {
        log.debug("Update employee info. employeeId={}", employeeId);

        Employee employee = findEmployeeById(employeeId);

        String address = request.getAddress();
        String phoneNumber = request.getPhoneNumber();
        employee.update(address, phoneNumber);

        log.info("Update employee info. employeeId={}", employeeId);
        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    public EmployeeInfo updateEmployeeStatus(Long employeeId, UpdateEmployeeStatusRequest request) {
        log.debug("Update employeeStatus start. employeeId={}, status={}", employeeId, request.getStatus());

        String status = request.getStatus();
        EmployeeStatus newStatus = EmployeeStatus.getStatusByName(status);
        if(newStatus == null) {
            log.info("Invalid status value. status={}", status);
            throw new ServiceResultCodeException(EmployeeResultCode.INVALID_VALUE);
        }

        Employee employee = findEmployeeById(employeeId);
        employee.changeStatus(newStatus);
        employee = employeeRepository.save(employee);

        log.info("Success to update employee status. employeeId={}, status={}", employeeId, status);
        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    protected Employee makeNewEmployee(RegisterEmployeeRequest request, Branch branch) {
        return Employee
                .builder()
                .branch(branch)
                .name(request.getName())
                .responsibility(request.getResponsibility())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .status(request.getStatus())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .build();
    }

    private Employee findEmployeeById(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isEmpty()) {
            log.info("Not exist employee by id. employeeId={}", employeeId);
            throw new ServiceResultCodeException(EmployeeResultCode.NOT_EXIST_EMPLOYEE);
        }

        return optionalEmployee.get();
    }
}
