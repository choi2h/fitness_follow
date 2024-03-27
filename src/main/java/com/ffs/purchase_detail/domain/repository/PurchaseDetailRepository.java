package com.ffs.purchase_detail.domain.repository;

import com.ffs.purchase_detail.domain.PurchaseDetail;
import org.springframework.data.repository.Repository;

public interface PurchaseDetailRepository extends Repository<PurchaseDetail, Long> {

    PurchaseDetail save(PurchaseDetail purchaseDetail);

}
