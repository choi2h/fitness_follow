package com.ffs.web.branch.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBranchRequest {
    private String name;
    private String address;
    private String phoneNumber;
}
