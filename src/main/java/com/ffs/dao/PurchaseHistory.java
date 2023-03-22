package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "PURCHASE_HISTORY")
public class PurchaseHistory {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "DATETIME")
    private LocalDate dateTime;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "COMMENT")
    private String comment;
}
