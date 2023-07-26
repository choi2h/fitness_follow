package com.ffs.pt.domain.repository;

import com.ffs.pt.domain.Pt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PtRepository extends Repository<Pt, Long> {

    Pt save(Pt pt);

    @Query("SELECT p FROM Pt p WHERE p.member.id = :memberId")
    List<Pt> findAllByMemberId(Long memberId);

    @Query("SELECT p FROM Pt p WHERE p.member.id = :memberId AND p.totalCount > p.useCount ORDER BY p.purchaseDate DESC")
    Optional<Pt> findByMemberIdAndPurchaseDate(Long memberId);
}
