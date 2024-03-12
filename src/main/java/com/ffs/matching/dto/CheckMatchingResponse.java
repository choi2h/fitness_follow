package com.ffs.matching.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckMatchingResponse {
    private Long memberId;
    private Long employeeId;
    private boolean isMatched;
}
