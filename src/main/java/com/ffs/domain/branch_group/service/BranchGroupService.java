package com.ffs.domain.branch_group.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.branch_group.BranchGroup;
import com.ffs.web.branch_group.request.RegisterBranchGroupRequest;
import com.ffs.domain.branch_group.repository.BranchGroupRepository;
import com.ffs.domain.branch.BranchResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchGroupService {

    private final BranchGroupRepository branchGroupRepository;

    public Long registerNewBranchGroup(RegisterBranchGroupRequest registerRequest) {
        log.debug("Register new branch group. name={}", registerRequest.getName());

        BranchGroup branchGroup = makeBranchGroup(registerRequest);
        log.trace("Make new branch group. branchGroup={}", branchGroup);

        branchGroup = branchGroupRepository.save(branchGroup);
        log.debug("Success to register branch group. id={}", branchGroup.getId());

        return branchGroup.getId();
    }

    public List<BranchGroup> getAllBranchGroup() {
        log.debug("Select all branch group.");
        List<BranchGroup> branchGroupList = branchGroupRepository.findAll();

        if(branchGroupList.isEmpty()) {
            log.debug("Not exist branch group anyone.");
            throw new ServiceResultCodeException(BranchResultCode.NO_REGISTERED_GROUPS);
        }

        log.debug("Found all branch group. count={}", branchGroupList.size());
        return branchGroupList;
    }

    public BranchGroup getBranchGroup(Long id) {
        log.debug("Select branch group by id. id={}", id);
        Optional<BranchGroup> optionalBranchGroup = branchGroupRepository.findById(id);

        if(optionalBranchGroup.isEmpty()) {
            log.debug("Not exist branch group. id={}", id);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH_GROUP, id);
        }

        BranchGroup branchGroup = optionalBranchGroup.get();
        log.debug("Found branch group from id. id={}, name={}", id, branchGroup.getName());

        return branchGroup;
    }

    private BranchGroup makeBranchGroup(RegisterBranchGroupRequest registerRequest) {
        String name = registerRequest.getName();

        BranchGroup branchGroup = new BranchGroup();
        branchGroup.setName(name);
        return branchGroup;
    }
}