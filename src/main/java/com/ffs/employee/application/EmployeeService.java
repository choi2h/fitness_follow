package com.ffs.employee.application;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.branch.domain.Branch;
import com.ffs.employee.dto.EmployeeInfo;
import com.ffs.employee.domain.Employee;
import com.ffs.employee.dto.RegisterEmployeeRequest;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.employee.domain.repository.EmployeeRepository;
import com.ffs.branch.BranchResultCode;
import com.ffs.employee.EmployeeResultCode;
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
    private final EmployeeMapperService employeeMapperService;

    /**
     * 신규 직원 등록
     * @param request
     * @return
     */
    public EmployeeInfo registerNewEmployee(RegisterEmployeeRequest request) {
        log.debug("Register new Employee. name={}", request.getName());

        Long branchId = request.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch for register employee. id={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        Employee employee = employeeMapperService.makeNewEmployee(request, branch);
        employee = employeeRepository.save(employee);

        log.debug("Success to register employee. id={}, name={}", employee.getId(), employee.getName());
        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    /**
     * 전체 직원 조회
     * @return
     */
    public List<EmployeeInfo> getAllEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.isEmpty()) {
            log.debug("Not exist employee anyone.");
            throw new ServiceResultCodeException(EmployeeResultCode.NO_REGISTERED_EMPLOYEE);
        }

        return employeeMapperService.employeeListToEmployeeInfoList(employeeList);
    }

    /**
     * 직원ID로 특정 직원 조회 *
     * @param id
     * @return
     */
    public EmployeeInfo getEmployeeById(Long id) {
        log.debug("Search employee by id. id={}", id);

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isEmpty()) {
            log.debug("Not exist employee from id. id={}", id);
            throw new ServiceResultCodeException(EmployeeResultCode.NOT_EXIST_EMPLOYEE, id);
        }

        Employee employee = optionalEmployee.get();
        log.debug("Found employee by id. id={}, name={}", id, employee.getName());

        return employeeMapperService.employeeToEmployeeInfo(employee);
    }

    /**
     * 특정 지점의 직원 리스트 조회
     * @param branchId
     * @return
     */
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

    //TODO 직원은 개인정보를 변경할 수 있다.


    /**
     * 직원 상태(재직중/퇴사) 변경
     * @param employeeId
     * @param request
     * @return
     */
    public EmployeeInfo updateEmployeeStatus(Long employeeId, UpdateEmployeeStatusRequest request) {
        String status = request.getStatus();
        Employee.Status newStatus = Employee.Status.getStatusByName(status);
        if(newStatus == null) {
            log.debug("Invalid status value. status={}", status);
            throw new ServiceResultCodeException(EmployeeResultCode.INVALID_VALUE);
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isEmpty()) {
            log.debug("Not exist employee for update employee status. employeeId={}", employeeId);
            throw new ServiceResultCodeException(EmployeeResultCode.NOT_EXIST_EMPLOYEE);
        }

        Employee employee = optionalEmployee.get();
        employee.setStatus(newStatus);
        employee = employeeRepository.save(employee);

        return employeeMapperService.employeeToEmployeeInfo(employee);
    }
}
