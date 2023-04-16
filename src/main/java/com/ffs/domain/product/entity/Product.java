package com.ffs.domain.product.entity;

import com.ffs.domain.branch.entity.Branch;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product { //(기본회원권, PT, 이벤트, 사물함, 운동복)
    @Id
    @Column(name = "PRODUCT_ID")
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

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "USE_YN")
    private Boolean isUse;

    @Builder
    public Product(Branch branch, String name, BigDecimal price, String type, LocalDate startDate, LocalDate endDate) {
        this.branch = branch;
        this.name = name;
        this.price = price;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;

        this.isUse = startDate.compareTo(LocalDate.now()) < 0 ;
    }

    public void updateUseYn(boolean isUse) {
        this.isUse = isUse;
    }

}
