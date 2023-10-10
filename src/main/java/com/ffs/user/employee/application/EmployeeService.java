package com.ffs.user.employee.application;

import com.ffs.branch.BranchResultCode;
import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.user.Role;
import com.ffs.user.UserResultCode;
import com.ffs.user.employee.domain.Employee;
import com.ffs.user.employee.domain.EmployeeStatus;
import com.ffs.user.employee.domain.repository.EmployeeRepository;
import com.ffs.user.employee.dto.EmployeeInfo;
import com.ffs.user.employee.dto.RegisterEmployeeRequest;
import com.ffs.user.employee.dto.UpdateEmployeeRequest;
import com.ffs.user.employee.dto.UpdateEmployeeStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public EmployeeInfo registerNewEmployee(RegisterEmployeeRequest request) {
        log.debug("Register new Employee start. name={}", request.getName());

        Long branchId = request.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch for register employee. id={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        Role role = getRoleByRoleName(request.getRole());
        Employee employee = makeNewEmployee(request, branch, role);
        employee = employeeRepository.save(employee);

        log.info("Success to register employee. id={}, name={}", employee.getId(), employee.getName());
        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    public List<EmployeeInfo> getAllEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();

        if(employeeList.isEmpty()) {
            log.debug("Not exist employee anyone.");
            throw new ServiceResultCodeException(UserResultCode.NO_REGISTERED_EMPLOYEE);
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

    public EmployeeInfo getEmployeeByBranchIdAndEmployeeId(Long branchId, Long employeeId) {
        log.debug("Search employee by id. branchId={}, employeeId={}", branchId, employeeId);

        Employee employee = findEmployeeById(employeeId);
        Long employeeBranchId = employee.getBranch().getId();
        if(!employeeBranchId.equals(branchId)) {
            log.info("Not have permission for employee. myBranchId={}, employeeBranchId={}", branchId, employeeBranchId);
            throw new ServiceResultCodeException(UserResultCode.NOT_HAVE_PERMISSION_FOR_EMPLOYEE);
        }

        log.debug("Found employee by id. id={}, name={}", employeeId, employee.getName());

        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    public List<EmployeeInfo> getEmployeeByBranchId(Long branchId) {
        log.debug("Search employee list by branch id. branchId={}", branchId);

        List<Employee> employeeList = employeeRepository.findAllByBranchId(branchId);
        if(employeeList.isEmpty()) {
            log.debug("Not exist employee by branch id. branchId={}", branchId);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_EMPLOYEE_FOR_BRANCH, branchId);
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
            throw new ServiceResultCodeException(UserResultCode.INVALID_VALUE);
        }

        Employee employee = findEmployeeById(employeeId);
        employee.changeStatus(newStatus);
        employee = employeeRepository.save(employee);

        log.info("Success to update employee status. employeeId={}, status={}", employeeId, status);
        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    protected Employee makeNewEmployee(RegisterEmployeeRequest request, Branch branch, Role role) {
        String password = passwordEncoder.encode(request.getPassword());
        return Employee
                .builder()
                .branch(branch)
                .name(request.getName())
                .role(role)
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .status(request.getStatus())
                .loginId(request.getLoginId())
                .password(password)
                .build();
    }

    private Employee findEmployeeById(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isEmpty()) {
            log.info("Not exist employee by id. employeeId={}", employeeId);
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_EMPLOYEE);
        }

        return optionalEmployee.get();
    }

    private Role getRoleByRoleName(String roleName) {
        try {
            return Role.getRoleByName(roleName);
        } catch (IllegalStateException e) {
            throw new ServiceResultCodeException(UserResultCode.INVALID_ROLE);
        }
    }
}
