package com.ffs.domain.locker;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum LockerResultCode implements ResultCode<String>{
    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NO_REGISTER_LOCKER("L001", "등록되어 있는 사물함이 없습니다.",HttpStatus.NOT_FOUND),
    NOT_EXIST_LOCKER("L002", "해당 번호의 사물함이 없습니다.",HttpStatus.NOT_FOUND),
    ALREADY_USED("L003", "이미 사용중인 사물함입니다.", HttpStatus.BAD_REQUEST)
    ;

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    LockerResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    LockerResultCode(ResultCode<String> resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), resultCode.getStatus());
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getMessage(Object... variables) {
        return null;
    }
}
