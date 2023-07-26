package com.ffs.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeeRequest {

    @NotNull
    private Long branchId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String responsibility;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String status;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
