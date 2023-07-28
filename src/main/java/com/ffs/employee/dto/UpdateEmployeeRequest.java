package com.ffs.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeRequest {

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

}
