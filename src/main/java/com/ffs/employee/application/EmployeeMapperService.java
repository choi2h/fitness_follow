package com.ffs.employee.application;

import com.ffs.branch.domain.Branch;
import com.ffs.employee.dto.EmployeeInfo;
import com.ffs.employee.domain.Employee;
import com.ffs.employee.dto.RegisterEmployeeRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapperService {
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

    protected List<EmployeeInfo> employeeListToEmployeeInfoList(List<Employee> employeeList) {
        List<EmployeeInfo> employeeInfoList = new ArrayList<>();
        for(Employee employee : employeeList) {
            EmployeeInfo employeeInfo = employeeToEmployeeInfo(employee);
            employeeInfoList.add(employeeInfo);
        }

        return employeeInfoList;
    }

    protected EmployeeInfo employeeToEmployeeInfo(Employee employee) {
        return EmployeeInfo
                .builder()
                .employeeId(employee.getId())
                .loginId(employee.getLoginId())
                .branchName(employee.getBranch().getName())
                .address(employee.getAddress())
                .phoneNumber(employee.getPhoneNumber())
                .name(employee.getName())
                .responsibility(employee.getResponsibility())
                .status(employee.getStatus())
                .build();
    }
}
