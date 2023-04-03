package com.ffs.web.locker.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterLockerRequest {
    private Long branchId;
    private int count;
}
