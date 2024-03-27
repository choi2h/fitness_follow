package com.ffs.purchase_history.domain;

import com.ffs.branch.domain.Branch;
import com.ffs.purchase_detail.domain.PurchaseDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @Column(name = "PURCHASE_DATE")
    private LocalDateTime dateTime;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "COMMENT")
    private String comment;

    @OneToMany(mappedBy = "purchaseHistory")
    private List<PurchaseDetail> purchaseDetail;

    @Builder
    public PurchaseHistory(Branch branch, Long memberId, String memberName, Long employeeId, String employeeName,
                           LocalDateTime dateTime, BigDecimal price, String comment) {
        this.branch = branch;
        this.memberId = memberId;
        this.memberName = memberName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.dateTime = dateTime;
        this.price = price;
        if(comment != null && !comment.isEmpty()) {
            this.comment = comment;
        }
    }
}
