package com.ffs.web.locker.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class RegisterLockerRequest {

    @NotNull
    private Long branchId;

    @NotNull
    private int count;
}
