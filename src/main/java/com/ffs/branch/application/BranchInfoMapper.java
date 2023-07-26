package com.ffs.branch.application;

import com.ffs.branch.domain.Branch;
import com.ffs.branch.dto.BranchInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BranchInfoMapper {

    protected BranchInfo convertBranchToBranchInfo(Branch branch) {
        return BranchInfo.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .groupId(branch.getBranchGroup().getId())
                .build();
    }

    protected List<BranchInfo> convertBranchListToBranchInfoList(List<Branch> branchList) {
        List<BranchInfo> branchInfoList = new ArrayList<>();

        for(Branch branch : branchList) {
            BranchInfo branchInfo = this.convertBranchToBranchInfo(branch);
            branchInfoList.add(branchInfo);
        }

        return branchInfoList;
    }
}
