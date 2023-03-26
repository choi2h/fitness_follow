package com.ffs.common.exception;

import com.ffs.common.ResultCode;
import lombok.Getter;

public class ServiceResultCodeException extends HttpStatusException{

    @Getter
    private final ResultCode<?> resultCode;

    @Getter
    private final Object[] objects;

    public ServiceResultCodeException(ResultCode<?> resultCode, Object... objects) {
        super(resultCode.getStatus(), resultCode.getMessage(objects));
        this.resultCode = resultCode;
        this.objects = objects;
    }

   public ServiceResultCodeException(ResultCode<?> resultCode, Throwable cause, Object... objects) {
        super(resultCode.getStatus(), resultCode.getMessage(objects), cause);
        this.resultCode = resultCode;
        this.objects = objects;
   }
}
