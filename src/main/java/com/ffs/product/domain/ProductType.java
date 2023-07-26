package com.ffs.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {

    MEMBERSHIP("기본 회원권"),
    PT_MEMBERSHIP("PT"),
    EVENT("이벤트"),
    LOCKER("사물함"),
    CLOTHES("운동복");

    private String text;
}
