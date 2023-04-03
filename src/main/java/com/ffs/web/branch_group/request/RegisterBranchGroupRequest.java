package com.ffs.web.branch_group.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBranchGroupRequest {

    @NotEmpty
    private String name;
}
