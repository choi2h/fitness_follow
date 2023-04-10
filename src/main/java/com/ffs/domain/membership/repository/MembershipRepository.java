package com.ffs.domain.membership.repository;

import com.ffs.domain.membership.entity.Membership;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MembershipRepository extends Repository<Membership, Long> {

    Membership save(Membership membership);

    @Query("SELECT m FROM Membership m WHERE m.member.id = :memberId")
    Optional<Membership> findByMemberId(Long memberId);

}
