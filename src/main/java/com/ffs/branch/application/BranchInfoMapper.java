package com.ffs.branch.application;

import com.ffs.branch.domain.Branch;
import com.ffs.branch.dto.BranchInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BranchInfoMapper {

    protected BranchInfo convertBranchToBranchInfo(Branch branch) {
        return BranchInfo.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .groupId(Objects.requireNonNullElse(branch.getBranchGroup().getId(), 0L))
                .build();
    }

    protected List<BranchInfo> convertBranchesToBranchInfos(List<Branch> branches) {
        List<BranchInfo> branchInfos = new ArrayList<>();

        for(Branch branch : branches) {
            BranchInfo branchInfo = this.convertBranchToBranchInfo(branch);
            branchInfos.add(branchInfo);
        }

        return branchInfos;
    }
}
