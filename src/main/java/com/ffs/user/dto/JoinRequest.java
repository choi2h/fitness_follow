package com.ffs.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequest {

    @NotNull
    private Long branchId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String status;

    @NotEmpty
    private String userType;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
