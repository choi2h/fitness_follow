package com.ffs.member.service;

import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.member.application.MemberService;
import com.ffs.member.domain.Member;
import com.ffs.member.domain.repository.MemberRepository;
import com.ffs.member.dto.RegisterMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    MemberService memberService;

    @Mock
    BranchRepository branchRepository;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원을 등록할 수 있다.")
    void registerMemberTest() {
        // given
        Member member = Member.builder().name("최이화").build();
        doReturn(Optional.of(new Branch())).when(branchRepository).findById(1L);
        doReturn(member).when(memberRepository).save(any(Member.class));
        RegisterMemberRequest request = getRegisterMemberRequest();

        // when
        Member result = memberService.registerNewMember(request);

        // then
        assertEquals(member.getName(), result.getName());
    }

    @Test
    @DisplayName("전체 회원을 조회할 수 있다.")
    void getAllMemberTest() {
        // given
        List<Member> memberList = getMemberList();
        doReturn(memberList).when(memberRepository).findAll();

        // when
        List<Member> resultList = memberService.getAllMember();

        // then
        assertEquals(memberList.size(), resultList.size());
    }

    @Test
    @DisplayName("특정 지점의 전체 회원을 조회할 수 있다.")
    void getMemberListByBranchIdTest() {
        // given
        List<Member> memberList = getMemberList();
        doReturn(memberList).when(memberRepository).findAllByBranchId(1L);

        // when
        List<Member> resultList = memberService.getMemberListByBranchId(1L);

        // then
        assertEquals(memberList.size(), resultList.size());
    }

    private RegisterMemberRequest getRegisterMemberRequest() {
        return new RegisterMemberRequest
                (1L, "최이화", "일반회원", "서울시", "010-0000-0000", "qwe", "1234");
    }

    private List<Member> getMemberList() {
        List<Member> memberList = new ArrayList<>();

        for(int i=0; i<5; i++) {
            Member member = new Member();
            memberList.add(member);
        }

        return memberList;
    }

}