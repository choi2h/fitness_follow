package com.ffs.membership.application;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.membership.MembershipResultCode;
import com.ffs.membership.domain.Membership;
import com.ffs.membership.domain.repository.MembershipRepository;
import com.ffs.user.domain.User;
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

    public Membership registerMembership(User user, LocalDate startDate, long durationDay) {
        LocalDate endDate = startDate.plusDays(durationDay);
        int sleepCount = (int) getSleepCount(durationDay);
        Membership membership = getNewMembership(user, startDate, endDate, sleepCount);

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

    private Membership getNewMembership(User user, LocalDate startDate, LocalDate endDate, int sleepCount) {
        return Membership
                .builder()
                .user(user)
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
