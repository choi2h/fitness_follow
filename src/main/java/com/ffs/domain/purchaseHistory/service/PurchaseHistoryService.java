package com.ffs.domain.purchaseHistory.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.branch.BranchResultCode;
import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch.repository.BranchRepository;
import com.ffs.domain.employee.Employee;
import com.ffs.domain.employee.repository.EmployeeRepository;
import com.ffs.domain.member.Member;
import com.ffs.domain.member.repository.MemberRepository;
import com.ffs.domain.purchaseHistory.PurchaseHistoryResultCode;
import com.ffs.domain.purchaseHistory.entity.PurchaseHistory;
import com.ffs.domain.purchaseHistory.repository.PurchaseHistoryRepository;
import com.ffs.domain.employee.EmployeeResultCode;
import com.ffs.domain.member.MemberResultCode;
import com.ffs.web.purchase_history.request.RegisterPurchaseHistoryRequest;
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
    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;

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

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty()) {
            log.debug("Not exist member. memberId={}", memberId);
            throw new ServiceResultCodeException(MemberResultCode.NOT_EXIST_MEMBER, memberId);
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isEmpty()) {
            log.debug("Not exist employee. employeeId={}", employeeId);
            throw new ServiceResultCodeException(EmployeeResultCode.NOT_EXIST_EMPLOYEE, employeeId);
        }

        Branch branch = optionalBranch.get();
        Member member = optionalMember.get();
        Employee employee = optionalEmployee.get();
        PurchaseHistory purchaseHistory = makePurchaseHistory(branch, member, employee, request);

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
                id, purchaseHistory.getBranch().getId(), purchaseHistory.getMember().getId());

        return purchaseHistory;
    }


    private PurchaseHistory makePurchaseHistory(Branch branch, Member member, Employee employee, RegisterPurchaseHistoryRequest request) {
        return PurchaseHistory
                .builder()
                .branch(branch)
                .member(member)
                .productId(request.getProductId())
                .employee(employee)
                .dateTime(request.getDateTime())
                .price(request.getPrice())
                .comment(request.getComment())
                .build();
    }
}
