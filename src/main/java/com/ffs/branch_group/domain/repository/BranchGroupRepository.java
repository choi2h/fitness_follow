package com.ffs.branch_group.domain.repository;

import com.ffs.branch_group.domain.BranchGroup;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface BranchGroupRepository extends Repository<BranchGroup, Long> {

    BranchGroup save(BranchGroup branchGroup);

    List<BranchGroup> findAll();

    Optional<BranchGroup> findById(Long id);
}
