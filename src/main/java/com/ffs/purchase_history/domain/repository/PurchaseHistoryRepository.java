package com.ffs.purchase_history.domain.repository;

import com.ffs.purchase_history.domain.PurchaseHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PurchaseHistoryRepository extends Repository<PurchaseHistory, Long> {

    PurchaseHistory save(PurchaseHistory purchaseHistory);

    Optional<PurchaseHistory> findById(Long id);

    @Query("SELECT p FROM PurchaseHistory p where p.branch.id = :branchId")
    List<PurchaseHistory> findAllByBranchId(Long branchId);

    @Query("SELECT p FROM PurchaseHistory p where p.memberId = :userId")
    List<PurchaseHistory> findAllByUserId(Long userId);
}
