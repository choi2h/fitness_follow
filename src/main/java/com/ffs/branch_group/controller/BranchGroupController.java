package com.ffs.branch_group.controller;

import com.ffs.branch_group.dto.BranchGroupInfo;
import com.ffs.branch_group.dto.RegisterBranchGroupRequest;
import com.ffs.branch_group.dto.BranchGroupResult;
import com.ffs.branch_group.application.BranchGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/branch-group")
@RequiredArgsConstructor
public class BranchGroupController {

    private final BranchGroupService branchGroupService;

    /**
     * 지점 그룹 등록
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> registerNewBranchGroup(@RequestBody @Valid RegisterBranchGroupRequest request) {
        BranchGroupInfo branchGroupInfo = branchGroupService.registerNewBranchGroup(request);
        BranchGroupResult response = BranchGroupResult.builder().branchGroup(branchGroupInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 모든 지점 그룹 조회
     */
    @GetMapping
    public ResponseEntity<Object> getAllBranchGroup() {
        List<BranchGroupInfo> branchGroupInfoList = branchGroupService.getAllBranchGroup();
        BranchGroupResult response = BranchGroupResult.builder().branchGroupList(branchGroupInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 지점 상세 정보 조회
    */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getBranchById(@PathVariable Long id) {
        BranchGroupInfo branchGroupInfo = branchGroupService.getBranchGroup(id);
        BranchGroupResult response = BranchGroupResult.builder().branchGroup(branchGroupInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
