package com.ffs.employee.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 직책
 */
@Getter
@RequiredArgsConstructor
public enum Responsibility {
    CEO("대표"),
    MANAGER("매니저"),
    TRAINER("트레이너");

    private final String text;
}
