package com.ffs.domain.locker.repository;

import com.ffs.domain.locker.entity.Locker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface LockerRepository extends Repository<Locker, Integer> {

    Iterable<Locker> saveAll(Iterable<Locker> lockerList);

    Locker save(Locker locker);

    Optional<Locker> findByLockerPk_NumberAndLockerPk_Branch_Id(int num, Long branchId);

    @Query("SELECT l FROM Locker l WHERE l.lockerPk.branch.id = :branchId")
    List<Locker> findAllByBranchId(Long branchId);
}
