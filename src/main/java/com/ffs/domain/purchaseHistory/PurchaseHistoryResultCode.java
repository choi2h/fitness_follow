package com.ffs.domain.purchaseHistory;

import com.ffs.common.DefaultResultCode;
import com.ffs.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum PurchaseHistoryResultCode implements ResultCode<String> {
    OK(DefaultResultCode.OK),
    FAIL(DefaultResultCode.FAILED),
    NOTHING_REGISTERED("C001", "등록되어 있는 상품이 없습니다.",HttpStatus.NOT_FOUND),
    NOT_EXIST_PURCHASE_HISTORY("C002", "존재하지 않는 구매 내역 입니다.",HttpStatus.BAD_REQUEST),
    NOT_EXIST_PURCHASE_HISTORY_FOR_BRANCH("C003", "지점에 관한 구매 내역이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_PURCHASE_HISTORY_FOR_MEMBER("C004", "회원에 관한 구매 내역이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

    @Getter
    private String code;

    private String message;

    @Getter
    private HttpStatus status;

    PurchaseHistoryResultCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = httpStatus;
    }

    PurchaseHistoryResultCode(ResultCode<String> resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), resultCode.getStatus());
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getMessage(Object... variables) {
        return null;
    }
}
