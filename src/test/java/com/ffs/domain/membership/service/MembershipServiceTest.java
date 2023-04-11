package com.ffs.domain.membership.service;

import com.ffs.domain.member.Member;
import com.ffs.domain.member.repository.MemberRepository;
import com.ffs.domain.membership.entity.Membership;
import com.ffs.domain.membership.repository.MembershipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @InjectMocks
    MembershipService membershipService;

    @Mock
    MembershipRepository membershipRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원은 멤버십을 등록할 수 있다.")
    void registerMembershipTest() {
        LocalDate localDate = LocalDate.now();
        long durationDay = 90;
        Member member = Member.builder().build();
        LocalDate endDate = localDate.plusDays(durationDay);
        Membership membership = Membership.builder().startDate(localDate).endDate(endDate).build();

        // given
        doReturn(membership).when(membershipRepository).save(any(Membership.class));

        // when
        Membership result = membershipService.registerMembership(member, localDate, durationDay);

        // then
        assertEquals(localDate, result.getStartDate());
        assertEquals(membership.getEndDate(), result.getEndDate());
    }

    @Test
    @Order(2)
    @DisplayName("회원의 회원권 정보를 조회할 수 있다.")
    void getMembershipTest() {
        // given
        Member member = Member.builder().name("최이화").build();
        LocalDate localDate = LocalDate.now();
        Membership membership = getNewMembership(member, localDate, localDate);

        long memberId = 1;
        doReturn(Optional.of(membership)).when(membershipRepository).findByMemberId(memberId);

        // when
        Membership result = membershipService.getMembershipById(memberId);

        // then
        assertEquals(membership.getStartDate(), result.getStartDate());
    }

    private Membership getNewMembership(Member member, LocalDate startDate, LocalDate endDate) {
        return Membership
                .builder()
                .member(member)
                .startDate(startDate)
                .endDate(endDate)
                .availableSleepCount(2)
                .build();
    }


}