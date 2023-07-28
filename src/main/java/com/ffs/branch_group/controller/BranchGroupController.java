package com.ffs.branch_group.controller;

import com.ffs.branch_group.dto.BranchGroupInfo;
import com.ffs.branch_group.dto.RegisterBranchGroupRequest;
import com.ffs.branch_group.dto.BranchGroupResult;
import com.ffs.branch_group.application.BranchGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/branch-group")
@RequiredArgsConstructor
public class BranchGroupController {

    private final BranchGroupService branchGroupService;

    @PostMapping
    public ResponseEntity<Object> registerNewBranchGroup(@RequestBody @Valid RegisterBranchGroupRequest request) {
        BranchGroupInfo branchGroupInfo = branchGroupService.registerNewBranchGroup(request);
        BranchGroupResult response = BranchGroupResult.builder().branchGroup(branchGroupInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBranchGroup() {
        List<BranchGroupInfo> branchGroupInfoList = branchGroupService.getAllBranchGroup();
        BranchGroupResult response = BranchGroupResult.builder().branchGroupList(branchGroupInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBranchById(@PathVariable Long id) {
        BranchGroupInfo branchGroupInfo = branchGroupService.getBranchGroup(id);
        BranchGroupResult response = BranchGroupResult.builder().branchGroup(branchGroupInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
