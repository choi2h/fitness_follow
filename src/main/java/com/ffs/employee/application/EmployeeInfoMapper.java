package com.ffs.employee.application;

import com.ffs.employee.dto.EmployeeInfo;
import com.ffs.employee.domain.Employee;
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
                .responsibility(employee.getResponsibility())
                .status(employee.getStatus())
                .build();
    }
}
