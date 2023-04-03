package com.ffs.web.locker.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RegisterLockerMemberRequest {
    private Long memberId;
    private LocalDate startDate;
    private LocalDate endDate;
}
