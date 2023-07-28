package com.ffs.branch_group.application;

import com.ffs.branch_group.domain.BranchGroup;
import com.ffs.branch_group.dto.BranchGroupInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BranchGroupInfoMapper {

    protected BranchGroupInfo convertBranchGroupToBranchGroupInfo(BranchGroup branchGroup) {
        return new BranchGroupInfo(branchGroup.getId(), branchGroup.getName());
    }

    protected List<BranchGroupInfo> convertBranchGroupListToBranchGroupInfoList(List<BranchGroup> branchGroupList) {
        List<BranchGroupInfo> branchGroupInfoList = new ArrayList<>();

        for(BranchGroup branchGroup : branchGroupList) {
            BranchGroupInfo branchGroupInfo = convertBranchGroupToBranchGroupInfo(branchGroup);
            branchGroupInfoList.add(branchGroupInfo);
        }

        return branchGroupInfoList;
    }
}
