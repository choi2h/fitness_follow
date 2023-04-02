package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT")
public class Product {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "TYPE")
    private String type; //(기본회원권, PT, 이벤트, 사물함, 운동복)

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "STOP_DATE")
    private LocalDate stopDate;

    @Column(name = "USE_YN")
    private Boolean isUse;

}
