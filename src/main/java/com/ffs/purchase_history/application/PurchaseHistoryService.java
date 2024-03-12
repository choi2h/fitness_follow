package com.ffs.purchase_history.application;

import com.ffs.branch.BranchResultCode;
import com.ffs.branch.domain.Branch;
import com.ffs.branch.domain.repository.BranchRepository;
import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.purchase_history.PurchaseHistoryResultCode;
import com.ffs.purchase_history.domain.PurchaseHistory;
import com.ffs.purchase_history.domain.repository.PurchaseHistoryRepository;
import com.ffs.purchase_history.dto.RegisterPurchaseHistoryRequest;
import com.ffs.user.UserResultCode;
import com.ffs.user.domain.User;
import com.ffs.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    public PurchaseHistory registerNewPurchaseHistory(RegisterPurchaseHistoryRequest request) {
        Long branchId = request.getBranchId();
        Long memberId = request.getMemberId();
        Long employeeId = request.getEmployeeId();
        log.debug("Register new purchase history. branchId={}, memberId={}, employeeId={}", branchId, memberId, employeeId);

        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch. branchId={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        Branch branch = optionalBranch.get();
        User memberUser = getUser(memberId);
        User employeeUser = getUser(employeeId);
        PurchaseHistory purchaseHistory = makePurchaseHistory(branch, memberUser, employeeUser, request);

        return purchaseHistoryRepository.save(purchaseHistory);
    }

    public List<PurchaseHistory> getAllPurchaseHistoryByBranchId(Long branchId) {
        log.debug("Search purchase history for branch. branchId={}", branchId);
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAllByBranchId(branchId);

        if(purchaseHistoryList.isEmpty()) {
            log.debug("Nothing registered purchase history for branch. branchId={}", branchId);
            throw new ServiceResultCodeException(PurchaseHistoryResultCode.NOT_EXIST_PURCHASE_HISTORY_FOR_BRANCH, branchId);
        }

        log.debug("Found purchase history for branch. branchId={}, count={}", branchId, purchaseHistoryList.size());
        return purchaseHistoryList;
    }

    // 회원별 구매 기록을 조회할 수 있다.
    public List<PurchaseHistory> getAllPurchaseHistoryByMemberId(Long memberId) {
        log.debug("Search purchase history for member. memberId={}", memberId);
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAllByMemberId(memberId);

        if(purchaseHistoryList.isEmpty()) {
            log.debug("Nothing registered purchase history for member. memberId={}", memberId);
            throw new ServiceResultCodeException(PurchaseHistoryResultCode.NOT_EXIST_PURCHASE_HISTORY_FOR_MEMBER, memberId);
        }

        log.debug("Found purchase history for member. memberId={}, count={}", memberId, purchaseHistoryList.size());
        return purchaseHistoryList;
    }

    public PurchaseHistory getPurchaseHistoryById(Long id) {
        log.debug("Search purchase history by id. id={}",id);
        Optional<PurchaseHistory> optionalPurchaseHistory = purchaseHistoryRepository.findById(id);

        if(optionalPurchaseHistory.isEmpty()) {
            log.debug("Not found purchase history by id. id={}", id);
            throw new ServiceResultCodeException(PurchaseHistoryResultCode.NOT_EXIST_PURCHASE_HISTORY, id);
        }

        PurchaseHistory purchaseHistory = optionalPurchaseHistory.get();
        log.debug("Found purchase history by id. id={}, branchId={}, memberId={}",
                id, purchaseHistory.getBranch().getId(), purchaseHistory.getMemberId());

        return purchaseHistory;
    }

    //TODO 직원별 매출 현황을 조회할 수 있다.

    //TODO 지점별 월 매출을 조회할 수 있다.



    //TODO 구매기록 삭제가 필요한가? -> 취소항목이 있어야 하나?
    // 구매기록이 삭제되면 구매 디테일도 삭제되어야 한다.


    private PurchaseHistory makePurchaseHistory(Branch branch, User memberUser, User employeeUser,
                                                RegisterPurchaseHistoryRequest request) {
        return PurchaseHistory
                .builder()
                .branch(branch)
                .memberId(memberUser.getId())
                .memberName(memberUser.getName())
                .employeeId(employeeUser.getId())
                .employeeName(employeeUser.getName())
                .dateTime(request.getDateTime())
                .price(request.getPrice())
                .comment(request.getComment())
                .build();
    }

    private User getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new ServiceResultCodeException(UserResultCode.NOT_EXIST_USER, id);
        }

        return userOptional.get();
    }
}
