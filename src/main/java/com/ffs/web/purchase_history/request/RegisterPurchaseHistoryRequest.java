package com.ffs.web.purchase_history.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RegisterPurchaseHistoryRequest {

    private Long branchId;
    private Long memberId;
    private Long productId;
    private Long employeeId;
    private LocalDateTime dateTime;
    private BigDecimal price;
    private String comment;

}
