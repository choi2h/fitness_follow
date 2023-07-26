package com.ffs.branch_group.service;

import com.ffs.branch_group.application.BranchGroupService;
import com.ffs.branch_group.domain.BranchGroup;
import com.ffs.branch_group.domain.repository.BranchGroupRepository;
import com.ffs.branch_group.dto.RegisterBranchGroupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BranchGroupServiceTest {

    @InjectMocks
    BranchGroupService branchGroupService;

    @Mock
    BranchGroupRepository branchGroupRepository;

    @Test
    @DisplayName("새로운 지점 그룹을 등록할 수 있다.")
    void registerBranchTest() {
        String name = "Y2GYM";

        // given
        BranchGroup branchGroup = getBranchGroup(name);
        doReturn(branchGroup).when(branchGroupRepository).save(any(BranchGroup.class));

        // when
        RegisterBranchGroupRequest request = new RegisterBranchGroupRequest(name);
        BranchGroup result = branchGroupService.registerNewBranchGroup(request);

        // then
        assertEquals(request.getName(), result.getName());
    }

    @Test
    @DisplayName("모든 지점 그룹을 조회할 수 있다.")
    void getAllBranchGroupTest() {
        // given
        List<BranchGroup> branchGroupList = getBranchGroupList();
        doReturn(branchGroupList).when(branchGroupRepository).findAll();

        // when
        List<BranchGroup> resultList = branchGroupService.getAllBranchGroup();

        // then
        assertEquals(branchGroupList.size(), resultList.size());

    }

    @Test
    @DisplayName("ID로 특정 지점 그룹을 조회할 수 있다.")
    void getBranchGroupTest() {
        long id = 1;
        String name = "Y2GYM";

        // given
        BranchGroup branchGroup = getBranchGroup(name);
        doReturn(Optional.of(branchGroup)).when(branchGroupRepository).findById(id);

        // when
        BranchGroup result = branchGroupService.getBranchGroup(id);

        // then
        assertEquals(name, result.getName());
    }

    private BranchGroup getBranchGroup(String name) {
        return BranchGroup.builder().name(name).build();
    }

    private List<BranchGroup> getBranchGroupList() {
        List<BranchGroup> branchGroupList = new ArrayList<>();

        for(int i=0; i<5; i++) {
            BranchGroup branchGroup = new BranchGroup();
            branchGroupList.add(branchGroup);
        }
        return branchGroupList;
    }

}