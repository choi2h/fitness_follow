package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "PT")
public class Pt {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "PURCHASE_DATE")
    private LocalDate purchaseDate;

    @Column(name = "TOTAL_COUNT")
    private Integer totalCount;

    @Column(name = "USE_COUNT")
    private Integer useCount;

    @Column(name = "PRICE_PER_SESSION")
    private BigDecimal pricePerSession;
}
