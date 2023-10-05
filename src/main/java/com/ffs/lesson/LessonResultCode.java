package com.ffs.lesson;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum LessonResultCode implements ResultCode<String> {
    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED);

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    LessonResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    LessonResultCode(ResultCode<String> resultCode) {
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
