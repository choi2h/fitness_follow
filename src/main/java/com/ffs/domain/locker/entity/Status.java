package com.ffs.domain.locker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    CAN_USE("사용가능"),
    IN_USE("사용중"),
    CAN_NOT_USE("사용불가"),
    EXPIRATION("만료");

    private String text;

}
