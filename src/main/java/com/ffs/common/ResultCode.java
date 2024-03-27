package com.ffs.common;

import org.springframework.http.HttpStatus;

public interface ResultCode<T> {

    T getCode();

    String getMessage();

    String getMessage(Object... variables);

    HttpStatus getStatus();

}
