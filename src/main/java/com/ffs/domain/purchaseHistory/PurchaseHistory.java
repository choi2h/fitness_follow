package com.ffs.domain.purchaseHistory;

import com.ffs.domain.member.Member;
import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.employee.Employee;
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
    @Column(name = "PURCHASE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @Column(name = "DATETIME")
    private LocalDate dateTime;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "COMMENT")
    private String comment;
}
