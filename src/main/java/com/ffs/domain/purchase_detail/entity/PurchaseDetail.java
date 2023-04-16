package com.ffs.domain.purchase_detail.entity;

import com.ffs.domain.product.entity.Product;
import com.ffs.domain.purchaseHistory.entity.PurchaseHistory;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "PURCHASE_DETAIL")
public class PurchaseDetail {

    @Id
    @Column(name = "PURCHASE_DETAIL_ID")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long purchaseDetailId;

    @ManyToOne
    @JoinColumn(name = "PURCHASE_ID")
    private PurchaseHistory purchaseHistory;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "MONTH")
    private int month; // 개월 수

    @Builder
    public PurchaseDetail(PurchaseHistory purchaseHistory, Product product, int month) {
        this.purchaseHistory = purchaseHistory;
        this.product = product;
        this.month = month;

    }

}
