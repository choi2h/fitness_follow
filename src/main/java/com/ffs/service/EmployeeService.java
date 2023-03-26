package com.ffs.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.dao.Branch;
import com.ffs.dao.Employee;
import com.ffs.dto.RegisterEmployeeRequest;
import com.ffs.repository.BranchRepository;
import com.ffs.repository.EmployeeRepository;
import com.ffs.util.BranchResultCode;
import com.ffs.util.EmployeeResultCode;
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

    public Long registerNewEmployee(RegisterEmployeeRequest request) {
        log.debug("Register new Employee. name={}", request.getName());

        Long branchId = request.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch for register employee. id={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        Employee employee = makeNewEmployee(request, branch);
        employee = employeeRepository.save(employee);

        return employee.getId();
    }

    public List<Employee> getAllEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.isEmpty()) {
            log.debug("Not exist employee anyone.");
            throw new ServiceResultCodeException(EmployeeResultCode.NO_REGISTERED_EMPLOYEE);
        }

        return employeeList;
    }

    public Employee getEmployeeById(Long id) {
        log.debug("Search employee by id. id={}", id);

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isEmpty()) {
            log.debug("Not exist employee from id. id={}", id);
            throw new ServiceResultCodeException(EmployeeResultCode.NOT_EXIST_EMPlOYEE, id);
        }

        Employee employee = optionalEmployee.get();
        log.debug("Found employee by id. id={}, name={}", id, employee.getName());

        return employee;
    }

    private Employee makeNewEmployee(RegisterEmployeeRequest request, Branch branch) {
        Employee employee = new Employee();
        employee.setBranch(branch);
        employee.setName(request.getName());
        employee.setResponsibility(request.getResponsibility());
        employee.setAddress(request.getAddress());
        employee.setStatus(request.getStatus());
        employee.setLoginId(request.getLoginId());
        employee.setPassword(request.getPassword());

        return employee;
    }
}
