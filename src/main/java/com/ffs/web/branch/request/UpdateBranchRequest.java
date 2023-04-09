package com.ffs.web.branch.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBranchRequest {

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;
}
