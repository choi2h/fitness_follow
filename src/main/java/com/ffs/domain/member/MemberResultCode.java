package com.ffs.domain.member;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum MemberResultCode implements ResultCode<String> {

    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NO_REGISTERED_MEMBER("M001", "등록되어 있는 회원이 없습니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_MEMBER("M002", "존재하는 않는 회원입니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_MEMBER_FOR_BRANCH("M003", "해당 지점에 등록되어있는 회원이 없습니다.", HttpStatus.NOT_FOUND)
    ;

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    MemberResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    MemberResultCode(ResultCode<String> resultCode) {
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
