package com.ffs.domain.pt.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.member.Member;
import com.ffs.domain.pt.PtInfo;
import com.ffs.domain.pt.PtResultCode;
import com.ffs.domain.pt.entity.Pt;
import com.ffs.domain.pt.repository.PtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PtService {

    private final PtRepository ptRepository;

    public Pt registerPt(Member member, int totalCount, BigDecimal pricePerSession) {
        log.debug("Register new pt membership. memberId={}, totalCount={} pricePerSession={}",
                member.getId(), totalCount, pricePerSession);
        Pt pt = getNewPt(member, totalCount, pricePerSession);

        return ptRepository.save(pt);
    }

    public void updateUseCount(Long memberId) {
        Optional<Pt> optionalPt = ptRepository.findByMemberIdAndPurchaseDate(memberId);
        if(optionalPt.isEmpty()) {
            log.debug("Not exist pt membership. memberId={}", memberId);
            throw new ServiceResultCodeException(PtResultCode.NOT_EXIST_PT, memberId);
        }

        Pt pt = optionalPt.get();
        pt.plusUseCount();
        ptRepository.save(pt);
    }

    public PtInfo getPtCount(Long memberId) {
        List<Pt> ptList = ptRepository.findAllByMemberId(memberId);

        if(ptList.isEmpty()) {
            log.debug("Not exist pt membership. memberId={}", memberId);
            return null;
        }

        int totalCount = 0;
        int useCount = 0;

        for(Pt pt : ptList) {
            totalCount += pt.getTotalCount();
            useCount += pt.getUseCount();
        }

        log.debug("Found pt count by member id. memberId={}, totalCount={}, useCount={}", memberId, totalCount, useCount);
        return getPtInfo(totalCount, useCount);
    }

    private PtInfo getPtInfo(int totalCount, int useCount) {
        return PtInfo
                .builder()
                .totalCount(totalCount)
                .useCount(useCount)
                .build();
    }


    private Pt getNewPt(Member member, int totalCount, BigDecimal pricePerSession) {
        return Pt
                .builder()
                .member(member)
                .totalCount(totalCount)
                .useCount(0)
                .purchaseDate(LocalDate.now())
                .pricePerSession(pricePerSession)
                .build();
    }
}
