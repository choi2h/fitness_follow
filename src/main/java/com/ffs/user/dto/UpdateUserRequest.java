package com.ffs.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

}
