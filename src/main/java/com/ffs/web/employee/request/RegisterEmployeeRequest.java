package com.ffs.web.employee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeeRequest {

    @NotEmpty
    private Long branchId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String responsibility;

    @NotEmpty
    private String address;

    @NotEmpty
    private String status;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
