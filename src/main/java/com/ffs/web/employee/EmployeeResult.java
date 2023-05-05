package com.ffs.web.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import com.ffs.domain.employee.EmployeeInfo;
import com.ffs.domain.employee.employee.Employee;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResult extends AbstractResponse {

    private static final long serialVersionUID = 4494064209636802405L;

    private Long id;

    private EmployeeInfo employee;

    private List<EmployeeInfo> employeeList;
}
