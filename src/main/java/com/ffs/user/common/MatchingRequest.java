package com.ffs.user.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingRequest {
    private Long employeeId;
    private Long memberId;
}
