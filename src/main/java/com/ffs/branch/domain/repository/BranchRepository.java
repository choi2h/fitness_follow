package com.ffs.branch.domain.repository;

import com.ffs.branch.domain.Branch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends Repository<Branch, Long> {

    Branch save(Branch branch);

    List<Branch> findAll();

    Optional<Branch> findById(Long id);

    @Query("SELECT b FROM Branch  b " +
            "WHERE b.branchGroup.id = :groupId")
    List<Branch> findAllByBranchGroup(Long groupId);

}
