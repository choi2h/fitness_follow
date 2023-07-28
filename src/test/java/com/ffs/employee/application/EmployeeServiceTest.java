package com.ffs.employee.application;

import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.employee.domain.EmployeeStatus;
import com.ffs.employee.dto.EmployeeInfo;
import com.ffs.employee.domain.Employee;
import com.ffs.employee.domain.repository.EmployeeRepository;
import com.ffs.employee.dto.RegisterEmployeeRequest;
import com.ffs.employee.dto.UpdateEmployeeStatusRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    private EmployeeInfoMapper employeeMapperService;


    @Test
    @DisplayName("신규 직원을 등록하고 조회할 수 있다.")
    void registerNewEmployee() {
        long branchId = 1;
        String name = "최이화";

        // given
        Branch branch = getBranch();
        doReturn(Optional.of(branch)).when(branchRepository).findById(branchId);

        Employee employee = getEmployee(name);
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        // when
        RegisterEmployeeRequest request = getRegisterEmployeeRequest(branchId, name);
        EmployeeInfo result = employeeService.registerNewEmployee(request);

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
        List<EmployeeInfo> resultList = employeeService.getEmployeeByBranchId(branchId);

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
        List<EmployeeInfo> resultList = employeeService.getAllEmployee();

        // then
        assertEquals(employeeList.size(), resultList.size());
    }

    @Test
    @DisplayName("직원의 상태(재직/퇴사) 정보를 변경할 수 있다.")
    void updateEmployeeStatus() {
        long employeeId = 1;

        //given
        Employee employee = getEmployee("최이화");
        doReturn(Optional.of(employee)).when(employeeRepository).findById(employeeId);
        employee.changeStatus(EmployeeStatus.RESIGN);
        doReturn(employee).when(employeeRepository).save(employee);

        //when
        String updateStatus = "퇴사";
        UpdateEmployeeStatusRequest request = new UpdateEmployeeStatusRequest(updateStatus);
        EmployeeInfo employeeInfo = employeeService.updateEmployeeStatus(employeeId, request);

        //then
        assertEquals(updateStatus, employeeInfo.getStatus());
    }


    private RegisterEmployeeRequest getRegisterEmployeeRequest(long brandId, String name) {
        return new RegisterEmployeeRequest
                (brandId, name, "매니저", "서울시 송파구", "010-0000-0000", "재직중", "chldlghk","1234");
    }

    private Employee getEmployee(String name) {
        return Employee
                .builder()
                .branch(getBranch())
                .status(EmployeeStatus.EMPLOYED.getName())
                .name(name)
                .address("")
                .phoneNumber("")
                .loginId("")
                .build();
    }

    private List<Employee> getEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();

        for(int i=0; i<5; i++) {
            Employee employee = getEmployee("test");
            employeeList.add(employee);
        }

        return employeeList;
    }

    private Branch getBranch() {
        return Branch
                .builder()
                .name("잠실점")
                .build();
    }
}