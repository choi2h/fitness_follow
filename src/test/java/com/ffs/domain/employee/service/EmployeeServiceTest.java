package com.ffs.domain.employee.service;

import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch.repository.BranchRepository;
import com.ffs.domain.branch_group.BranchGroup;
import com.ffs.domain.branch_group.repository.BranchGroupRepository;
import com.ffs.domain.employee.Employee;
import com.ffs.web.employee.request.RegisterEmployeeRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BranchGroupRepository branchGroupRepository;

    @Autowired
    private BranchRepository branchRepository;

    private static Branch branch;

    @BeforeAll
    void registerBranch() {
        BranchGroup branchGroup = new BranchGroup();
        branchGroup.setName("Y2GYM");
        BranchGroup saveBranchGroup = branchGroupRepository.save(branchGroup);

        Branch saveBranch = Branch
                .builder()
                .name("Y2GYM 가락점")
                .phoneNumber("010-0000-0000")
                .address("서울시 송파구")
                .branchGroup(saveBranchGroup)
                .build();
        branch  = branchRepository.save(saveBranch);
    }

    @Test
    @Order(1)
    @DisplayName("신규 직원을 등록하고 조회할 수 있다.")
    void registerNewEmployee() {
        String name = "최이화";
        RegisterEmployeeRequest request = new RegisterEmployeeRequest
                (branch.getId(), name, "매니저", "서울시 송파구", "재직중", "chldlghk","1234");
        Long employeeId = employeeService.registerNewEmployee(request);

        Employee employee = employeeService.getEmployeeById(employeeId);
        assertEquals(name, employee.getName());
    }

    @Test
    @Order(2)
    @DisplayName("지점에 해당하는 모든 직원의 정보를 조회할 수 있다.")
    void getAllEmployeeListByBranchId() {
        List<Employee> employeeList = employeeService.getEmployeeByBranchId(branch.getId());
        assertEquals(1, employeeList.size());
    }

    @Test
    @Order(3)
    @DisplayName("모든 직원의 정보를 조회할 수 있다.")
    void getAllEmployee() {
        List<Employee> employeeList = employeeService.getAllEmployee();
        assertEquals(1, employeeList.size());
    }

}