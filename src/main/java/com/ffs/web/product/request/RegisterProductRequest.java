package com.ffs.web.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RegisterProductRequest {

    private Long branchId;
    private String name;
    private BigDecimal price;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String useYn;
}
