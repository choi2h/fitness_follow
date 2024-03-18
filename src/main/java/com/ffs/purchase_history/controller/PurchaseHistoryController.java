package com.ffs.purchase_history.controller;

import com.ffs.purchase_history.domain.PurchaseHistory;
import com.ffs.purchase_history.application.PurchaseHistoryService;
import com.ffs.purchase_history.dto.PurchaseHistoryResult;
import com.ffs.purchase_history.dto.RegisterPurchaseHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    /**
     * 구매 기록 등록
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER')")
    public ResponseEntity<Object> registerPurchaseHistory(@RequestBody @Valid RegisterPurchaseHistoryRequest request) {
        PurchaseHistory purchaseHistory = purchaseHistoryService.registerNewPurchaseHistory(request);
        PurchaseHistoryResult result = PurchaseHistoryResult.builder().purchaseHistory(purchaseHistory).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 지점의 구매 기록 조회
     */
    @GetMapping("/branch/{branch_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER')")
    public ResponseEntity<Object> getPurchaseHistoryListByBranchId(@PathVariable("branch_id") Long id) {
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryService.getAllPurchaseHistoryByBranchId(id);
        PurchaseHistoryResult result = PurchaseHistoryResult.builder().purchaseHistoryList(purchaseHistoryList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 회원의 구매 기록 조회
     */
    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_MEMBER')")
    public ResponseEntity<Object> getPurchaseHistoryListByUserId(@PathVariable("user_id") Long id) {
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryService.getAllPurchaseHistoryByUserId(id);
        PurchaseHistoryResult result = PurchaseHistoryResult.builder().purchaseHistoryList(purchaseHistoryList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 구매기록 상세 조회
     */
    @GetMapping("/{purchase_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_MEMBER')")
    public ResponseEntity<Object> getPurchaseHistoryById(@PathVariable("purchase_id") Long id) {
        PurchaseHistory purchaseHistory = purchaseHistoryService.getPurchaseHistoryById(id);
        PurchaseHistoryResult result = PurchaseHistoryResult.builder().purchaseHistory(purchaseHistory).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
