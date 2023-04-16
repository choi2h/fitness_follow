package com.ffs.domain.purchase_detail.repository;

import com.ffs.domain.purchase_detail.entity.PurchaseDetail;
import org.springframework.data.repository.Repository;

public interface PurchaseDetailRepository extends Repository<PurchaseDetail, Long> {

    PurchaseDetail save(PurchaseDetail purchaseDetail);

}
