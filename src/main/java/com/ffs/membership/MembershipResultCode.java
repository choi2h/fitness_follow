package com.ffs.membership;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum MembershipResultCode implements ResultCode<String> {

    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NOT_EXIST_MEMBERSHIP("S001", "등록되어 있는 회원권이 없습니다.", HttpStatus.BAD_REQUEST),
    ;

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    MembershipResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    MembershipResultCode(ResultCode<String> resultCode) {
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
