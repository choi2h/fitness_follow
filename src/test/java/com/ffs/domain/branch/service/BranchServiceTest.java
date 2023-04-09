package com.ffs.domain.branch.service;

import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch_group.BranchGroup;
import com.ffs.web.branch.request.RegisterBranchRequest;
import com.ffs.web.branch.request.UpdateBranchRequest;
import com.ffs.domain.branch_group.repository.BranchGroupRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BranchServiceTest {

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchGroupRepository branchGroupRepository;

    private static Long branchGroupId;
    private static Long id;
    private static String name;

    @BeforeAll
    void registerBranchGroup() {
        BranchGroup branchGroup = new BranchGroup();
        branchGroup.setName("Y2GYM");
        BranchGroup result = branchGroupRepository.save(branchGroup);

        branchGroupId = result.getId();
        name = "Y2GYM 가락점";
    }

    @DisplayName("지점이 등록되어야 한다.")
    @Test
    @Order(1)
    void registerBranch() {
        RegisterBranchRequest request =
                new RegisterBranchRequest(branchGroupId, name, "가락동", "02-5221-1251");

        long branchId = branchService.registerBranch(request);

        id = branchId;
        assertEquals(1, branchId);
    }

    @DisplayName("모든 지점이 조회되어야 한다.")
    @Test
    @Order(2)
    void getAllBranch() {
        List<Branch> branchList = branchService.getAllBranch();

        assertEquals(1, branchList.size());
    }

    @DisplayName("브랜치 그룹에 해당하는 모든 지점이 조회되어야 한다.")
    @Test
    @Order(3)
    void getAllBranchByGroupId() {
        List<Branch> branchList = branchService.getAllBranchByBranchGroupId(1L);
        assertEquals(1, branchList.size());
    }

    @DisplayName("ID에 해당하는 지점이 조회되어야 한다.")
    @Test
    @Order(4)
    void getBranchById() {
        Branch branch = branchService.getBranchById(id);

        assertEquals(name, branch.getName());
    }

    @DisplayName("ID에 해당하는 지점의 정보가 수정되어야 한다.")
    @Test
    @Order(5)
    void updateBranchById() {
        String address = "garak-dong";
        String phoneNumber = "0000-0000";
        UpdateBranchRequest request = new UpdateBranchRequest(address, phoneNumber);
        branchService.updateBranchById(id, request);

        Branch branch = branchService.getBranchById(id);
        assertEquals(address, branch.getAddress());
        assertEquals(phoneNumber, branch.getPhoneNumber());
    }

}