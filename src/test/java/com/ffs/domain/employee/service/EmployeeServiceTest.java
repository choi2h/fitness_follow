package com.ffs.domain.employee.service;

import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch.repository.BranchRepository;
import com.ffs.domain.employee.Employee;
import com.ffs.domain.employee.repository.EmployeeRepository;
import com.ffs.web.employee.request.RegisterEmployeeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BranchRepository branchRepository;

    @Test
    @DisplayName("신규 직원을 등록하고 조회할 수 있다.")
    void registerNewEmployee() {
        long branchId = 1;
        String name = "최이화";

        // given
        Branch branch = Branch.builder().build();
        doReturn(Optional.of(branch)).when(branchRepository).findById(branchId);

        Employee employee = getEmployee(name);
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        // when
        RegisterEmployeeRequest request = getRegisterEmployeeRequest(branchId, name);
        Employee result = employeeService.registerNewEmployee(request);

        // then
        assertEquals(request.getName(), result.getName());
    }

    @Test
    @DisplayName("지점에 해당하는 모든 직원의 정보를 조회할 수 있다.")
    void getAllEmployeeListByBranchId() {
        long branchId = 1;

        // given
        List<Employee> employeeList = getEmployeeList();
        doReturn(employeeList).when(employeeRepository).findAllByBranchId(branchId);

        // when
        List<Employee> resultList = employeeService.getEmployeeByBranchId(branchId);

        // then
        assertEquals(employeeList.size(), resultList.size());
    }

    @Test
    @DisplayName("모든 직원의 정보를 조회할 수 있다.")
    void getAllEmployee() {
        // given
        List<Employee> employeeList = getEmployeeList();
        doReturn(employeeList).when(employeeRepository).findAll();

        // when
        List<Employee> resultList = employeeService.getAllEmployee();

        // then
        assertEquals(employeeList.size(), resultList.size());
    }


    private RegisterEmployeeRequest getRegisterEmployeeRequest(long brandId, String name) {
        return new RegisterEmployeeRequest
                (brandId, name, "매니저", "서울시 송파구", "재직중", "chldlghk","1234");
    }

    private Employee getEmployee(String name) {
        return Employee
                .builder()
                .name(name)
                .build();
    }

    private List<Employee> getEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();

        for(int i=0; i<5; i++) {
            Employee employee = new Employee();
            employeeList.add(employee);
        }

        return employeeList;
    }
}