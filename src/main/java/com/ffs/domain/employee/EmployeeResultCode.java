package com.ffs.domain.employee;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum EmployeeResultCode implements ResultCode<String> {

    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NO_REGISTERED_EMPLOYEE("E001", "등록되어 있는 직원이 없습니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_EMPLOYEE("E002", "존재하는 않는 직원입니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_EMPLOYEE_FOR_BRANCH("E003", "해당 지점에 근무하는 직원 정보가 없습니다.", HttpStatus.NOT_FOUND)
    ;

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    EmployeeResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    EmployeeResultCode(ResultCode<String> resultCode) {
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
