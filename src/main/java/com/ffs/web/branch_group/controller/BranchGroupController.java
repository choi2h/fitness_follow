package com.ffs.web.branch_group.controller;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.branch_group.BranchGroup;
import com.ffs.web.branch_group.request.RegisterBranchGroupRequest;
import com.ffs.web.branch_group.BranchGroupResult;
import com.ffs.domain.branch_group.service.BranchGroupService;
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
        BranchGroup branchGroup = branchGroupService.registerNewBranchGroup(request);
        BranchGroupResult response = BranchGroupResult.builder().branchGroup(branchGroup).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBranchGroup() {
        List<BranchGroup> branchGroups;

        try {
            branchGroups = branchGroupService.getAllBranchGroup();
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        BranchGroupResult response = BranchGroupResult.builder().branchGroupList(branchGroups).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBranchById(@PathVariable Long id) {
        BranchGroup branchGroup;

        try {
            branchGroup = branchGroupService.getBranchGroup(id);
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        BranchGroupResult response = BranchGroupResult.builder().branchGroup(branchGroup).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
