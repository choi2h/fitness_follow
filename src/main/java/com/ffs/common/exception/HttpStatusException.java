package com.ffs.common.exception;

import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

public class HttpStatusException extends RuntimeException{

    @Getter
    private final HttpStatus status;

    HttpStatusException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    HttpStatusException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    HttpStatusException(HttpStatus status, Throwable cause) {
        this(status, status.toString(), cause);
    }

    HttpStatusException(HttpStatus status, String message, Object... objects) {
        this(status, MessageFormatter.arrayFormat(message, objects).getMessage());
    }

    HttpStatusException(HttpStatus status, String message, Throwable cause, Object... objects) {
        this(status, MessageFormatter.arrayFormat(message, objects).getMessage(), cause);
    }

}
