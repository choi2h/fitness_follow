package com.ffs.user;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum UserResultCode implements ResultCode<String> {

    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),

    // Member
    NO_REGISTERED_MEMBER("M001", "등록되어 있는 회원이 없습니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_MEMBER("M002", "존재하는 않는 회원입니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_MEMBER_FOR_BRANCH("M003", "해당 지점에 등록되어있는 회원이 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("M004", "비밀번호가 일치하지 않습니다..", HttpStatus.BAD_REQUEST),


    //Employee
    NO_REGISTERED_EMPLOYEE("E001", "등록되어 있는 직원이 없습니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_EMPLOYEE("E002", "존재하는 않는 직원입니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_EMPLOYEE_FOR_BRANCH("E003", "해당 지점에 근무하는 직원 정보가 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_VALUE("E004", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    INVALID_ROLE("E005", "잘못된 권한 정보입니다.", HttpStatus.BAD_REQUEST)
    ;

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    UserResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    UserResultCode(ResultCode<String> resultCode) {
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
