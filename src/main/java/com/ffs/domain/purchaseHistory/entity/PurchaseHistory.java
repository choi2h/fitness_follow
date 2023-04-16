package com.ffs.domain.purchaseHistory.entity;

import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.employee.Employee;
import com.ffs.domain.member.Member;
import com.ffs.domain.purchase_detail.entity.PurchaseDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "purchaseHistory")
    private List<PurchaseDetail> purchaseDetail;

    @Column(name = "DATETIME")
    private LocalDateTime dateTime;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "COMMENT")
    private String comment;

    @Builder
    public PurchaseHistory(Branch branch, Member member, Long productId, Employee employee, LocalDateTime dateTime, BigDecimal price, String comment) {
        this.branch = branch;
        this.member = member;
        this.productId = productId;
        this.employee = employee;
        this.dateTime = dateTime;
        this.price = price;
        if(comment != null && !comment.isEmpty()) {
            this.comment = comment;
        }
    }
}
