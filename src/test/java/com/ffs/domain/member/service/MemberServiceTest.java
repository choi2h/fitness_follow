package com.ffs.domain.member.service;

import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch.repository.BranchRepository;
import com.ffs.domain.branch_group.BranchGroup;
import com.ffs.domain.branch_group.repository.BranchGroupRepository;
import com.ffs.domain.member.Member;
import com.ffs.domain.member.repository.MemberRepository;
import com.ffs.web.member.request.RegisterMemberRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberServiceTest {
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchGroupRepository branchGroupRepository;

    @Autowired
    private MemberService memberService;

    private static Branch branch;

    @BeforeAll
    void registerMember() {
        BranchGroup branchGroup = registerBranchGroup();
        branch = registerBranch(branchGroup);
    }

    @Test
    @Order(1)
    @DisplayName("회원을 등록할 수 있다.")
    void registerEmployeeTest() {
        Long branchId = branch.getId();
        String name = "최이화";
        RegisterMemberRequest request = new RegisterMemberRequest
                (branchId, name, "일반회원", "서울시 송파구", "010-0000-0000", "qwe", "1234");

        Member saveMember = memberService.registerNewMember(request);
        Member getMember = memberService.getMemberById(saveMember.getId());

        assertEquals(name, getMember.getName());
    }

    @Test
    @Order(2)
    @DisplayName("전체 회원을 조회할 수 있다.")
    void getAllMemberTest() {
        List<Member> memberList = memberService.getAllMember();

        assertEquals(1, memberList.size());
    }

    @Test
    @Order(3)
    @DisplayName("특정 지점의 전체 회원을 조회할 수 있다.")
    void getMemberListByBranchIdTest() {
        List<Member> memberList = memberService.getMemberListByBranchId(branch.getId());

        assertEquals(1, memberList.size());
    }

    private Branch registerBranch(BranchGroup branchGroup) {
        Branch saveBranch = Branch
                .builder()
                .name("Y2GYM 가락점")
                .phoneNumber("010-0000-0000")
                .address("서울시 송파구")
                .branchGroup(branchGroup)
                .build();
        return branchRepository.save(saveBranch);
    }

    private BranchGroup registerBranchGroup() {
        BranchGroup branchGroup = new BranchGroup();
        branchGroup.setName("Y2GYM");
        return branchGroupRepository.save(branchGroup);
    }
}