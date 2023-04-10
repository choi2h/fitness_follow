package com.ffs.domain.membership.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.member.Member;
import com.ffs.domain.membership.MembershipResultCode;
import com.ffs.domain.membership.entity.Membership;
import com.ffs.domain.membership.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembershipService {

    private static final int SLEEP_COUNT_RANGE = 6;

    private final MembershipRepository membershipRepository;

    public Membership registerMembership(Member member, LocalDate startDate, long durationDay) {
        LocalDate endDate = startDate.plusDays(durationDay);
        int sleepCount = (int) getSleepCount(durationDay);
        Membership membership = getNewMembership(member, startDate, endDate, sleepCount);

        return membershipRepository.save(membership);
    }

    public Membership getMembershipById(Long memberId) {
        log.debug("Search membership by member id. memberId={}", memberId);
        Optional<Membership> optionalMembership = membershipRepository.findByMemberId(memberId);

        if(optionalMembership.isEmpty()) {
            log.debug("Not exist membership. memberId={}", memberId);
            throw new ServiceResultCodeException(MembershipResultCode.NOT_EXIST_MEMBERSHIP, memberId);
        }

        return optionalMembership.get();
    }

    private Membership getNewMembership(Member member, LocalDate startDate, LocalDate endDate, int sleepCount) {
        return Membership
                .builder()
                .member(member)
                .startDate(startDate)
                .endDate(endDate)
                .availableSleepCount(sleepCount)
                .build();
    }

    private long getSleepCount(long durationDay) {
        long durationMonth = durationDay /30;
        if(durationMonth > SLEEP_COUNT_RANGE) {
            return durationMonth / SLEEP_COUNT_RANGE;
        }

        return 0;
    }


}
