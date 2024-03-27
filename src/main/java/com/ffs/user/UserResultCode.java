package com.ffs.user;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum UserResultCode implements ResultCode<String> {

    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),

    // USER
    NO_REGISTERED_USER("U001", "등록되어 있는 회원이 없습니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_USER("U002", "존재하는 않는 회원입니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_USER_FOR_BRANCH("U003", "해당 지점에 등록되어있는 회원이 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("U004", "비밀번호가 일치하지 않습니다..", HttpStatus.BAD_REQUEST),
    NOT_HAVE_PERMISSION_FOR_USER("U005", "회원에 대한 권한이 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_VALUE("U006", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    INVALID_ROLE("U007", "잘못된 권한 정보입니다.", HttpStatus.BAD_REQUEST)
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
