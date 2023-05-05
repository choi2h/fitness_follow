package com.ffs.domain.employee;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfo {

    private Long employeeId;

    private String loginId;

    private String branchName;

    private String name;

    private String responsibility;

    private String address;

    private String phoneNumber;

    private String status;
}

