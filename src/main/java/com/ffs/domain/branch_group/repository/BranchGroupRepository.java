package com.ffs.domain.branch_group.repository;

import com.ffs.domain.branch_group.BranchGroup;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface BranchGroupRepository extends Repository<BranchGroup, Long> {

    BranchGroup save(BranchGroup branchGroup);

    List<BranchGroup> findAll();

    Optional<BranchGroup> findById(Long id);
}
