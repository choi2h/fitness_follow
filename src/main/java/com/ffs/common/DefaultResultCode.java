package com.ffs.common;

import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

public enum DefaultResultCode implements ResultCode<String> {

    OK("0", "정상입니다.", HttpStatus.OK),
    FAILED("-1", "에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    @Getter
    private final String code;

    private final String message;

    @Getter
    private final HttpStatus status;

    DefaultResultCode(String code, String message) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.OK;
    }

    DefaultResultCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMessage(Object... variables) {
        return MessageFormatter.arrayFormat(this.message, variables).getMessage();
    }

}
