package com.ffs.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.dao.Branch;
import com.ffs.dao.BranchGroup;
import com.ffs.dto.RegisterBranchRequest;
import com.ffs.dto.UpdateBranchRequest;
import com.ffs.repository.BranchGroupRepository;
import com.ffs.repository.BranchRepository;
import com.ffs.util.BranchResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchGroupRepository branchGroupRepository;
    private final BranchRepository branchRepository;

    public Long registerBranch(RegisterBranchRequest request) {
        log.debug("Register new branch. name={}, groupId={}", request.getName(), request.getGroupId());

        Long branchGroupId = request.getGroupId();
        Optional<BranchGroup> optionalBranchGroup = branchGroupRepository.findById(branchGroupId);
        if(optionalBranchGroup.isEmpty()) {
            log.debug("Not exist branch from id. id={}", branchGroupId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH_GROUP, branchGroupId);
        }

        Branch branch = makeNewBranch(request, optionalBranchGroup.get());
        log.trace("Make new branch. name={}", branch.getName());

        branch = branchRepository.save(branch);
        log.debug("Success to register new branch. id={}", branch.getId());

        return branch.getId();
    }

    public List<Branch> getAllBranch() {
        log.debug("Search all branch.");

        List<Branch> branchList = branchRepository.findAll();
        if(branchList.isEmpty()) {
            log.debug("Not exist branch anyone.");
            throw new ServiceResultCodeException(BranchResultCode.NOT_REGISTERED_BRANCH);
        }

        log.debug("Found all branch. count={}", branchList.size());
        return branchList;
    }

    public Branch getBranchById(Long id) {
        log.debug("Search branch by id. id={}", id);

        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch from id. id={}", id);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, id);
        }

        Branch branch = optionalBranch.get();
        log.debug("Found branch by id. id={}, name={}", id, branch.getName());
        return branch;
    }

    public Long updateBranchById(Long id, UpdateBranchRequest request) {
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isEmpty()) {
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, id);
        }

        Branch branch = optionalBranch.get();
        branch.setPhoneNumber(request.getPhoneNumber());
        branch.setAddress(request.getAddress());
        branch.setName(request.getName());
        branch = branchRepository.save(branch);

        return branch.getId();
    }

    public List<Branch> getAllBranchByBranchGroupId(Long branchGroupId) {
        List<Branch> branchList = branchRepository.findAllByBranchGroup(branchGroupId);
        if(branchList.isEmpty()) {
            log.debug("Not exist branch anyone.");
            throw new ServiceResultCodeException(BranchResultCode.NOT_REGISTERED_BRANCH);
        }

        return branchList;
    }

    private Branch makeNewBranch(RegisterBranchRequest request, BranchGroup branchGroup) {
        Branch branch = new Branch();
        branch.setBranchGroup(branchGroup);
        branch.setAddress(request.getAddress());
        branch.setName(request.getName());
        branch.setPhoneNumber(request.getPhoneNumber());

        return branch;
    }
}
