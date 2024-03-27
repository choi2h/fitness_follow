package com.ffs.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckMatchingResponse {
    private Long memberId;
    private String memberName;
    private Long employeeId;
    private String employeeName;
    private boolean isMatched;
}
