package com.ffs.lesson;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum LessonResultCode implements ResultCode<String> {
    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NOT_EXIST_LESSON("L001", "존재하지 않는 레슨 정보입니다.", HttpStatus.BAD_REQUEST),
    NOT_HAVE_PERSIST_FOR_LESSON("L002", "레슨에 대한 권한이 없습니다.", HttpStatus.BAD_REQUEST)
    ;

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
