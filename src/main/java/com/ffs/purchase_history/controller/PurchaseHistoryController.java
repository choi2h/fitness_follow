package com.ffs.purchase_history.controller;

import com.ffs.purchase_history.domain.PurchaseHistory;
import com.ffs.purchase_history.application.PurchaseHistoryService;
import com.ffs.purchase_history.dto.PurchaseHistoryResult;
import com.ffs.purchase_history.dto.RegisterPurchaseHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    @PostMapping
    public ResponseEntity<Object> registerPurchaseHistory(@RequestBody @Valid RegisterPurchaseHistoryRequest request) {
        PurchaseHistory purchaseHistory = purchaseHistoryService.registerNewPurchaseHistory(request);
        PurchaseHistoryResult result = PurchaseHistoryResult.builder().purchaseHistory(purchaseHistory).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<Object> getPurchaseHistoryListByBranchId(@PathVariable Long id) {
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryService.getAllPurchaseHistoryByBranchId(id);
        PurchaseHistoryResult result = PurchaseHistoryResult.builder().purchaseHistoryList(purchaseHistoryList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<Object> getPurchaseHistoryListByMemberId(@PathVariable Long id) {
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryService.getAllPurchaseHistoryByMemberId(id);
        PurchaseHistoryResult result = PurchaseHistoryResult.builder().purchaseHistoryList(purchaseHistoryList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPurchaseHistoryById(@PathVariable Long id) {
        PurchaseHistory purchaseHistory = purchaseHistoryService.getPurchaseHistoryById(id);
        PurchaseHistoryResult result = PurchaseHistoryResult.builder().purchaseHistory(purchaseHistory).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
