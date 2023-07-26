package com.ffs.branch.application;

import com.ffs.branch.dto.BranchInfo;
import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.branch.domain.Branch;
import com.ffs.branch_group.domain.BranchGroup;
import com.ffs.branch.dto.RegisterBranchRequest;
import com.ffs.branch.dto.UpdateBranchRequest;
import com.ffs.branch_group.domain.repository.BranchGroupRepository;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.branch.BranchResultCode;
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
    private final BranchInfoMapper branchInfoMapper;

    public BranchInfo registerBranch(RegisterBranchRequest request) {
        log.debug("Register new branch. name={}, groupId={}", request.getName(), request.getGroupId());

        Long branchGroupId = request.getGroupId();
        Optional<BranchGroup> optionalBranchGroup = branchGroupRepository.findById(branchGroupId);
        if(optionalBranchGroup.isEmpty()) {
            log.info("Not exist branch from id. id={}", branchGroupId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH_GROUP, branchGroupId);
        }

        Branch branch = makeNewBranch(request, optionalBranchGroup.get());
        log.trace("Make new branch. name={}", branch.getName());

        Branch result = branchRepository.save(branch);
        log.info("Success to register new branch. id={}, name={}", result.getId(), result.getName());

        return branchInfoMapper.convertBranchToBranchInfo(branch);
    }

    public List<BranchInfo> getAllBranch() {
        log.debug("Search all branch.");

        List<Branch> branchList = branchRepository.findAll();
        if(branchList.isEmpty()) {
            log.info("Not exist branch anyone.");
            throw new ServiceResultCodeException(BranchResultCode.NOT_REGISTERED_BRANCH);
        }

        log.info("Found all branch. count={}", branchList.size());
        return branchInfoMapper.convertBranchListToBranchInfoList(branchList);
    }

    public BranchInfo getBranchById(Long id) {
        log.debug("Search branch by id. id={}", id);

        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isEmpty()) {
            log.info("Not exist branch from id. id={}", id);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, id);
        }

        Branch branch = optionalBranch.get();
        log.info("Found branch by id. id={}, name={}", id, branch.getName());
        return branchInfoMapper.convertBranchToBranchInfo(branch);
    }

    public BranchInfo updateBranchById(Long id, UpdateBranchRequest request) {
        log.debug("Update branch info. id={}", id);
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isEmpty()) {
            log.info("Not exist branch from id. id={}", id);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, id);
        }

        Branch branch = optionalBranch.get();

        //TODO log를 어떻게 작성해야할까? if문을 사용하여 바뀐걸 확인하자니 branch 내부에서도 if검사를 해서 중복으로 검사되는게 싫고
        // 그냥 다 로그로 남기자니 쓸모없는 것까지 남기는 것 같고,,
        String address = request.getAddress();
        log.trace("Update branch address. origin={}, new={}", branch.getAddress(), request.getAddress());
        String phoneNumber = request.getPhoneNumber();
        log.trace("Update branch phone number. origin={}, new={}", branch.getPhoneNumber(), request.getPhoneNumber());

        branch.updateBranchInfo(address, phoneNumber);
        branch = branchRepository.save(branch);
        log.info("Success to update branch info. id={}", branch.getId());

        return branchInfoMapper.convertBranchToBranchInfo(branch);
    }

    public List<BranchInfo> getAllBranchByBranchGroupId(Long branchGroupId) {
        log.debug("Search all branch by branch group id. branchGroupId={}", branchGroupId);

        List<Branch> branchList = branchRepository.findAllByBranchGroup(branchGroupId);
        if(branchList.isEmpty()) {
            log.info("Nothing registered branch by branch group id. branchGroupId={}", branchGroupId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_REGISTERED_BRANCH);
        }

        log.info("Found branch by branch group id. branchGroupId={}, count={}", branchGroupId, branchList.size());
        return branchInfoMapper.convertBranchListToBranchInfoList(branchList);
    }

    private Branch makeNewBranch(RegisterBranchRequest request, BranchGroup branchGroup) {
        return Branch
                .builder()
                .branchGroup(branchGroup)
                .address(request.getAddress())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
