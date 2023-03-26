package com.ffs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBranchRequest {

    private String name;
    private String address;
    private String phoneNumber;
    private Long branchGroupId;

}
