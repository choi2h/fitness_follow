package com.ffs.pt.domain;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.member.domain.Member;
import com.ffs.pt.PtResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PT")
public class Pt {

    @Id
    @Column(name = "PT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "PURCHASE_DATE")
    private LocalDate purchaseDate;

    @Column(name = "TOTAL_COUNT")
    private int totalCount;

    @Column(name = "USE_COUNT")
    private int useCount;

    @Column(name = "PRICE_PER_SESSION")
    private BigDecimal pricePerSession;

    @Builder
    public Pt(Member member, LocalDate purchaseDate, int totalCount, int useCount, BigDecimal pricePerSession) {
        this.member = member;
        this.purchaseDate = purchaseDate;
        this.totalCount = totalCount;
        this.useCount = useCount;
        this.pricePerSession = pricePerSession;
    }

    public void plusUseCount() {
        if(totalCount == useCount) {
            throw new ServiceResultCodeException(PtResultCode.NOT_EXIST_IDLE_COUNT);
        }

        useCount++;
    }
}
