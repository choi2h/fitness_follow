package com.ffs.repository;

import com.ffs.dao.Branch;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends Repository<Branch, Long> {

    Branch save(Branch branch);

    List<Branch> findAll();

    Optional<Branch> findById(Long id);

}
