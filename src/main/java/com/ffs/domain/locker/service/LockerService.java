package com.ffs.domain.locker.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.branch.BranchResultCode;
import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch.repository.BranchRepository;
import com.ffs.domain.locker.LockerResultCode;
import com.ffs.domain.locker.entity.Locker;
import com.ffs.domain.locker.entity.LockerPk;
import com.ffs.domain.locker.entity.Status;
import com.ffs.domain.locker.repository.LockerRepository;
import com.ffs.domain.member.Member;
import com.ffs.domain.member.repository.MemberRepository;
import com.ffs.util.MemberResultCode;
import com.ffs.web.locker.request.RegisterLockerMemberRequest;
import com.ffs.web.locker.request.RegisterLockerRequest;
import com.ffs.web.locker.request.UpdateAvailableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockerService {

    private static final String YES  = "Y";

    private final LockerRepository lockerRepository;
    private final BranchRepository branchRepository;
    private final MemberRepository memberRepository;

    public void registerNewLocker(RegisterLockerRequest request) {
        Long branchId = request.getBranchId();
        int count = request.getCount();

        log.debug("Register new lockers. branchId={}, count={}", branchId, count);
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);

        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch. branchId={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        log.debug("Found branch by id for register new locker. branchId={}, name={}", branchId, branch.getName());

        List<Locker> insertLockerList = new ArrayList<>();
        for(int i=1; i<count+1; i++) {
            Locker locker = getNewLocker(i, branch);
            insertLockerList.add(locker);
        }

        lockerRepository.saveAll(insertLockerList);
    }

    public List<Locker> getAllLockerByBranchId(Long branchId) {
        log.debug("Search all locker by branchId. branchId={}", branchId);

        List<Locker> lockerList = lockerRepository.findAllByBranchId(branchId);
        if(lockerList.isEmpty()) {
            log.debug("Not exist locker anyone. branchId={}", branchId);
            throw new ServiceResultCodeException(LockerResultCode.NO_REGISTER_LOCKER, branchId);
        }

        log.debug("Found locker list by branch.  count={}", lockerList.size());
        return lockerList;
    }

    public Locker getLocker(Long branchId, int lockerNumber) {
        log.debug("Search locker by id. branchId={}, lockerNumber={}", branchId, lockerNumber);
        return getLocker(lockerNumber, branchId);
    }

    public Locker registerLockerMember(Long branchId, int lockerNumber, RegisterLockerMemberRequest request) {
        Locker locker = getLocker(lockerNumber, branchId);

        Long memberId = request.getMemberId();
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty()) {
            log.debug("Not exist member info for register locker. memberId={}", memberId);
            throw new ServiceResultCodeException(MemberResultCode.NOT_EXIST_MEMBER, memberId);
        }

        Member member = optionalMember.get();
        locker.registerMember(member, request.getStartDate(), request.getEndDate());

        return lockerRepository.save(locker);
    }

    public Locker updateLockerStatusToUseOrNot(Long branchId, int lockerNumber, UpdateAvailableRequest request) {
        Locker locker = getLocker(lockerNumber, branchId);

        boolean availableUpdateStatus = checkAvailableUpdateStatus(locker);
        if(!availableUpdateStatus) {
            throw new ServiceResultCodeException(LockerResultCode.ALREADY_USED, locker);
        }

        if(request.getUseYn().equals(YES)) {
            locker.initLocker();
        } else {
            locker.updateCanNotUseStatus();
        }
        return lockerRepository.save(locker);
    }

    private boolean checkAvailableUpdateStatus(Locker locker) {
        if(locker.getStatus().equals(Status.CAN_USE.getText())
                || locker.getStatus().equals(Status.EXPIRATION.getText())) {
            return true;
        }

        LocalDate endDate = locker.getEndDate();
        LocalDate current = LocalDate.now();
        return isOverEndDate(endDate, current);
    }

    private boolean isOverEndDate(LocalDate endDate,LocalDate current) {
        return endDate.compareTo(current) < 0;
    }

    private Locker getLocker(int lockerNumber, Long branchId) {
        Optional<Locker> optionalLocker = lockerRepository
                .findByLockerPk_NumberAndLockerPk_Branch_Id(lockerNumber, branchId);
        if(optionalLocker.isEmpty()) {
            log.debug("Not exist locker number. lockerNumber={}, branchId={}", lockerNumber, branchId);
            throw new ServiceResultCodeException(LockerResultCode.NOT_EXIST_LOCKER, lockerNumber);
        }

        return optionalLocker.get();
    }

    private Locker getNewLocker(int num, Branch branch) {
        LockerPk lockerPk = LockerPk
                .builder()
                .number(num)
                .branch(branch)
                .build();

        return Locker
                .builder()
                .lockerPk(lockerPk)
                .build();

    }
}
