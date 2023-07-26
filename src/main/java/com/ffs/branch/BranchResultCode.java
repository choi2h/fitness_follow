package com.ffs.branch;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BranchResultCode implements ResultCode<String> {

    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NO_REGISTERED_GROUPS("G001", "등록되어 있는 그룹이 없습니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_BRANCH_GROUP("G002", "존재하지 않는 그룹입니다.", HttpStatus.NOT_FOUND),
    NOT_REGISTERED_BRANCH("B001", "등록되어 있는 지점이 없습니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_BRANCH("B002", "존재하지 않는 지점입니다.", HttpStatus.NOT_FOUND)
    ;

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    BranchResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    BranchResultCode(ResultCode<String> resultCode) {
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
