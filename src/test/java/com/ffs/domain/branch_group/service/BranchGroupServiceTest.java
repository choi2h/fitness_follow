package com.ffs.domain.branch_group.service;

import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch_group.BranchGroup;
import com.ffs.web.branch_group.request.RegisterBranchGroupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BranchGroupServiceTest {

    @Autowired
    private BranchGroupService branchGroupService;

    @Test
    void registerBranchTest() {
        String name = "Y2GYM";
        RegisterBranchGroupRequest request = new RegisterBranchGroupRequest(name);
        Long branchGroupId = branchGroupService.registerNewBranchGroup(request);

        BranchGroup branchGroup = branchGroupService.getBranchGroup(branchGroupId);
        assertEquals(name, branchGroup.getName());
    }

    @Test
    void getAllBranchGroupTest() {
        List<BranchGroup> branchGroupList = branchGroupService.getAllBranchGroup();
        assertEquals(1, branchGroupList.size());

    }
}