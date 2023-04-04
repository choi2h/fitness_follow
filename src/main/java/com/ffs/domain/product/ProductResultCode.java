package com.ffs.domain.product;


import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ProductResultCode implements ResultCode<String> {

    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NOTHING_REGISTERED("P001", "등록되어 있는 상품이 없습니다.",HttpStatus.NOT_FOUND),
    NOT_EXIST_PRODUCT("P002", "존재하지 않는 상품입니다.",HttpStatus.BAD_REQUEST),
    NO_USE_PRODUCT("P003", "사용하지 않는 상품입니다.", HttpStatus.BAD_REQUEST);

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    ProductResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    ProductResultCode(ResultCode<String> resultCode) {
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
