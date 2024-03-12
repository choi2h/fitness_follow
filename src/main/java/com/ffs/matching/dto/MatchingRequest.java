package com.ffs.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingRequest {
    private Long employeeUserId;
    private Long memberUserId;
}
