package com.ffs.matching.domain.repository;

import com.ffs.matching.domain.UserMatching;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserMatchingRepository extends Repository<UserMatching, Long> {

    UserMatching save(UserMatching userMatching);

    List<UserMatching> findAllByMemberIdOrderByFinishedAt(Long memberId);

    List<UserMatching> findAllByEmployeeIdOrderByFinishedAt(Long employeeId);

    Optional<UserMatching> findByMemberIdAndEmployeeId(Long memberId, Long employeeId);
}
