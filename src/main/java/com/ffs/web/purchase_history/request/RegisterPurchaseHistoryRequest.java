package com.ffs.web.purchase_history.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RegisterPurchaseHistoryRequest {

    @NotNull
    private Long branchId;

    @NotNull
    private Long memberId;

    @NotNull
    private Long productId;

    @NotNull
    private Long employeeId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateTime;

    @NotNull
    private BigDecimal price;

    @NotEmpty
    private String comment;

}
