package com.ffs.web.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMemberRequest {

    @NotEmpty
    private Long branchId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String status;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
