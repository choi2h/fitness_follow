package com.ffs.pt;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum PtResultCode implements ResultCode<String> {

    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NOT_EXIST_PT("P001", "등록되어 있는 PT 회원권이 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_IDLE_COUNT("P002", "PT 횟수가 전부 소진되었습니다.", HttpStatus.BAD_REQUEST)
    ;

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    PtResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    PtResultCode(ResultCode<String> resultCode) {
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
