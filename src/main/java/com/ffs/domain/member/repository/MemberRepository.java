package com.ffs.domain.member.repository;

import com.ffs.domain.member.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();

    @Query("SELECT m FROM Member m WHERE m.branch.id = :id")
    List<Member> findAllByBranchId(Long id);
}