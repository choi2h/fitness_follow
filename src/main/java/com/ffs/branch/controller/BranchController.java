package com.ffs.branch.controller;

import com.ffs.branch.application.BranchService;
import com.ffs.branch.dto.BranchInfo;
import com.ffs.branch.dto.BranchResult;
import com.ffs.branch.dto.RegisterBranchRequest;
import com.ffs.branch.dto.UpdateBranchRequest;
import com.ffs.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    private ResponseEntity<Object> registerNewBranch(@RequestBody @Valid RegisterBranchRequest request) {
        BranchInfo branchInfo = branchService.registerBranch(request);
        BranchResult result = BranchResult.builder().branch(branchInfo).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO')")
    @GetMapping("/all")
    private ResponseEntity<Object> findAllBranch() {
        List<BranchInfo> branchInfoList = branchService.getAllBranch();
        BranchResult result = BranchResult.builder().branchList(branchInfoList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO')")
    @GetMapping("/{id}")
    private ResponseEntity<Object> findBranchById(@PathVariable Long id) {
        BranchInfo branchInfo = branchService.getBranchById(id);
        BranchResult result = BranchResult.builder().id(id).branch(branchInfo).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_CEO, ROLE_MANAGER')")
    @PutMapping("/{id}")
    private ResponseEntity<Object> updateBranchInfo(@PathVariable Long id, @RequestBody @Valid UpdateBranchRequest request) {
        BranchInfo branchInfo = branchService.updateBranchById(id, request);
        BranchResult branchResult = BranchResult.builder().id(id).branch(branchInfo).build();

        return new ResponseEntity<>(branchResult, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO')")
    @GetMapping("/all/{id}")
    public ResponseEntity<Object> findBranchListByGroupId(@PathVariable Long id) {
        List<BranchInfo> branchInfoList = branchService.getAllBranchByBranchGroupId(id);
        BranchResult result = BranchResult.builder().branchList(branchInfoList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
