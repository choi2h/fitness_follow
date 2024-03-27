package com.ffs.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid Token")
public class InvalidTokenException extends RuntimeException{

    @Getter
    private final HttpStatus resultCode;

    public InvalidTokenException(final String message){
        super(message);
        this.resultCode = HttpStatus.UNAUTHORIZED;
    }

    public InvalidTokenException() {
        this("유효하지 않은 토큰입니다.");
    }

}
