package com.ffs.branch.controller;

import com.ffs.branch.application.BranchService;
import com.ffs.branch.dto.BranchInfo;
import com.ffs.branch.dto.BranchResult;
import com.ffs.branch.dto.RegisterBranchRequest;
import com.ffs.branch.dto.UpdateBranchRequest;
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

    /**
     * 신규 지점 등록
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_CEO')")
    private ResponseEntity<Object> registerNewBranch(@RequestBody @Valid RegisterBranchRequest request) {
        BranchInfo branchInfo = branchService.registerBranch(request);
        BranchResult result = BranchResult.builder().branch(branchInfo).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 모든 지점 조회
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<Object> findAllBranch() {
        List<BranchInfo> branchInfoList = branchService.getAllBranch();
        BranchResult result = BranchResult.builder().branchList(branchInfoList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 지점ID로 지점 상세 정보 조회
     */
    @GetMapping("/{branch_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO')")
    private ResponseEntity<Object> findBranchById(@PathVariable("branch_id") Long id) {
        BranchInfo branchInfo = branchService.getBranchById(id);
        BranchResult result = BranchResult.builder().id(id).branch(branchInfo).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 지점 정보 수정
     */
    @PutMapping("/{branch_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_CEO')")
    private ResponseEntity<Object> updateBranchInfo(@PathVariable("branch_id") Long id, @RequestBody @Valid UpdateBranchRequest request) {
        BranchInfo branchInfo = branchService.updateBranchById(id, request);
        BranchResult branchResult = BranchResult.builder().id(id).branch(branchInfo).build();

        return new ResponseEntity<>(branchResult, HttpStatus.OK);
    }

    /**
     * 지점 그룹에 해당하는 지점들 조회
     */
    @GetMapping("/all/{branch_group_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO')")
    public ResponseEntity<Object> findBranchListByGroupId(@PathVariable("branch_group_id") Long groupId) {
        List<BranchInfo> branchInfoList = branchService.getAllBranchByBranchGroupId(groupId);
        BranchResult result = BranchResult.builder().branchList(branchInfoList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
