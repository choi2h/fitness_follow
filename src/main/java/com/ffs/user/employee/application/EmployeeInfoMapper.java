package com.ffs.user.employee.application;

import com.ffs.user.employee.dto.EmployeeInfo;
import com.ffs.user.employee.domain.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeInfoMapper {

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
                .responsibility(employee.getRole().getName())
                .status(employee.getStatus())
                .build();
    }
}
