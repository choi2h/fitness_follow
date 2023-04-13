package com.ffs.domain.locker.service;

import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch.repository.BranchRepository;
import com.ffs.domain.locker.entity.Locker;
import com.ffs.domain.locker.entity.LockerPk;
import com.ffs.domain.locker.entity.Status;
import com.ffs.domain.locker.repository.LockerRepository;
import com.ffs.domain.member.Member;
import com.ffs.domain.member.repository.MemberRepository;
import com.ffs.web.locker.request.RegisterLockerMemberRequest;
import com.ffs.web.locker.request.RegisterLockerRequest;
import com.ffs.web.locker.request.UpdateAvailableRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class LockerServiceTest {

    @InjectMocks
    LockerService lockerService;

    @Mock
    LockerRepository lockerRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    BranchRepository branchRepository;

    @Test
    @DisplayName("한 지점의 사물함 개수를 설정할 수 있어야 한다.")
    void registerNewLockerTest() {
        // given
        long branchId = 1;
        Branch branch = getBranch();
        doReturn(Optional.of(branch)).when(branchRepository).findById(branchId);
        List<Locker> lockers = getLockerList();
        doReturn(lockers).when(lockerRepository).saveAll(anyList());

        // when
        RegisterLockerRequest request = new RegisterLockerRequest(branchId, 4);
        List<Locker> result = lockerService.registerNewLocker(request);

        // then
        assertEquals(lockers.size(), result.size());
    }

    @Test
    @DisplayName("한 지점의 모든 사물함 정보를 조회할 수 있다.")
    void getAllLockersByBranchIdTest() {
        // given
        long branchId = 1;
        List<Locker> lockers = getLockerList();
        doReturn(lockers).when(lockerRepository).findAllByBranchId(branchId);

        // when
        List<Locker> resultList = lockerService.getAllLockerByBranchId(branchId);

        // then
        assertEquals(lockers.size(), resultList.size());
    }

    @Test
    @DisplayName("특정 지점의 특정 사물함 정보를 조회할 수 있다.")
    void getLockerTest() {
        // given
        long branchId = 2;
        int lockerNumber = 20;
        Branch branch = getBranch();
        Locker locker = getLocker(branch, lockerNumber);
        doReturn(Optional.of(locker)).when(lockerRepository)
                .findByLockerPk_NumberAndLockerPk_Branch_Id(lockerNumber, branchId);

        // when
        Locker result = lockerService.getLocker(branchId, lockerNumber);

        // then
        assertEquals(locker.getLockerPk().getBranch().getName(),  result.getLockerPk().getBranch().getName());
    }

    @Test
    @DisplayName("특정 회원이 할당된 사물함 정보를 조회할 수 있다.")
    void registerLockerMemberTest() {
        // given
        long branchId = 2;
        int lockerNumber = 20;
        Branch branch = getBranch();
        Locker locker = getLocker(branch, lockerNumber);
        doReturn(Optional.of(locker)).when(lockerRepository)
                .findByLockerPk_NumberAndLockerPk_Branch_Id(lockerNumber, branchId);

        long memberId = 2;
        Member member = getMember("최이화");
        doReturn(Optional.of(member)).when(memberRepository).findById(memberId);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);
        locker.registerMember(member, startDate, endDate);
        doReturn(locker).when(lockerRepository).save(locker);

        // when
        RegisterLockerMemberRequest request =
                new RegisterLockerMemberRequest(memberId, startDate, endDate);
        Locker result = lockerService.registerLockerMember(branchId, lockerNumber, request);

        // then
        assertEquals(member.getName(), result.getMember().getName());
    }

    @Test
    @DisplayName("사물함의 사용가능 여부를 변경할 수 있다.")
    void updateLockerStatusToUseOrNot() {
        // given
        long branchId = 2;
        int lockerNumber = 20;
        Branch branch = getBranch();
        Locker locker = getLocker(branch, lockerNumber);

        Member member = getMember("최이화");
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(30);
        locker.registerMember(member, startDate, endDate);

        doReturn(Optional.of(locker)).when(lockerRepository)
                .findByLockerPk_NumberAndLockerPk_Branch_Id(lockerNumber, branchId);

        locker.initLocker();
        doReturn(locker).when(lockerRepository).save(locker);

        // when
        UpdateAvailableRequest request = new UpdateAvailableRequest("Y");
        Locker result = lockerService.updateLockerStatusToUseOrNot(branchId, lockerNumber, request);

        // then
        assertEquals(Status.CAN_USE.getText(), result.getStatus());
    }

    Branch getBranch() {
        return Branch.builder().name("name").build();
    }

    Member getMember(String name) {
        return Member.builder().name(name).build();
    }

    Locker getLocker(Branch branch, int number) {
        LockerPk lockerPk = LockerPk.builder().branch(branch).number(number).build();
        return Locker.builder().lockerPk(lockerPk).build();
    }

    List<Locker> getLockerList() {
        List<Locker> lockers = new ArrayList<>();

        for(int i=0; i<5; i++) {
            Locker locker = Locker.builder().build();
            lockers.add(locker);
        }

        return lockers;
    }

}