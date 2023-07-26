package com.ffs.membership.domain.repository;

import com.ffs.membership.domain.Membership;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MembershipRepository extends Repository<Membership, Long> {

    Membership save(Membership membership);

    @Query("SELECT m FROM Membership m WHERE m.member.id = :memberId")
    Optional<Membership> findByMemberId(Long memberId);

}
