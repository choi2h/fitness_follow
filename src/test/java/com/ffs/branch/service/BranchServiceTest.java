package com.ffs.branch.service;

import com.ffs.branch.application.BranchService;
import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.branch_group.domain.BranchGroup;
import com.ffs.branch.dto.RegisterBranchRequest;
import com.ffs.branch.dto.UpdateBranchRequest;
import com.ffs.branch_group.domain.repository.BranchGroupRepository;
import org.junit.jupiter.api.*;
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
class BranchServiceTest {

    @InjectMocks
    BranchService branchService;

    @Mock
    BranchRepository branchRepository;

    @Mock
    BranchGroupRepository branchGroupRepository;

    @Test
    @DisplayName("새로운 지점을 등록할 수 있다.")
    void registerBranchTest() {
        // given
        long branchGroupId = 1;
        String name = "Y2GYM 가락점";
        Branch branch = Branch.builder().name(name).build();

        doReturn(Optional.of(BranchGroup.builder().build())).when(branchGroupRepository).findById(branchGroupId);
        doReturn(branch).when(branchRepository).save(any(Branch.class));

        // when
        RegisterBranchRequest request = getRegisterBranchRequest(branchGroupId, name);
        Branch result = branchService.registerBranch(request);

        // then
        assertEquals(request.getName(), result.getName());
    }

    @Test
    @DisplayName("모든 지점이 조회되어야 한다.")
    void getAllBranch() {
        // given
        List<Branch> branchList = getBranchList();
        doReturn(branchList).when(branchRepository).findAll();

        // when
        List<Branch> resultList = branchService.getAllBranch();

        // then
        assertEquals(branchList.size(), resultList.size());
    }

    @Test
    @DisplayName("브랜치 그룹에 해당하는 모든 지점이 조회되어야 한다.")
    void getAllBranchByGroupId() {
        long branchGroupId = 1;
        // given
        List<Branch> branchList = getBranchList();
        doReturn(branchList).when(branchRepository).findAllByBranchGroup(branchGroupId);

        // when
        List<Branch> resultList = branchService.getAllBranchByBranchGroupId(branchGroupId);

        // then
        assertEquals(branchList.size(), resultList.size());
    }

    @Test
    @DisplayName("ID에 해당하는 지점이 조회되어야 한다.")
    void getBranchById() {
       long branchId = 1;
       // given
        Branch branch = Branch.builder().name("Y2GYM 가락점").build();
        doReturn(Optional.of(branch)).when(branchRepository).findById(branchId);

        // when
        Branch result = branchService.getBranchById(branchId);

        // then
        assertEquals(branch.getName(), result.getName());
    }

    @Test
    @DisplayName("ID에 해당하는 지점의 정보가 수정되어야 한다.")
    void updateBranchById() {
        long branchId = 1;
        String address = "잠실동";
        String phoneNumber = "010-2222-2222";

        // given
        Branch branch = Branch.builder().address(address).phoneNumber(phoneNumber).build();
        doReturn(Optional.of(branch)).when(branchRepository).findById(branchId);
        doReturn(branch).when(branchRepository).save(any(Branch.class));


        // when
        UpdateBranchRequest request = getUpdateBranchRequest(address, phoneNumber);
        Branch result = branchService.updateBranchById(branchId, request);

        // then
        assertEquals(request.getAddress(), result.getAddress());
    }

    private RegisterBranchRequest getRegisterBranchRequest(Long branchGroupId, String name) {
        return new RegisterBranchRequest(branchGroupId, name, "가락동", "02-5221-1251");
    }

    private List<Branch> getBranchList() {
        List<Branch> branchList = new ArrayList<>();

        for(int i=0; i<5; i++) {
            Branch branch = new Branch();
            branchList.add(branch);
        }

        return branchList;
    }

    private UpdateBranchRequest getUpdateBranchRequest(String address, String phoneNumber) {
        return new UpdateBranchRequest(address, phoneNumber);
    }
}