package com.ffs.controller;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.dao.BranchGroup;
import com.ffs.dto.RegisterBranchGroupRequest;
import com.ffs.dto.BranchGroupResult;
import com.ffs.service.BranchGroupService;
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
        Long branchGroupId = branchGroupService.registerNewBranchGroup(request);
        BranchGroupResult response = BranchGroupResult.builder().id(branchGroupId).build();
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
