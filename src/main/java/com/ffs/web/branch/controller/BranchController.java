package com.ffs.web.branch.controller;

import com.ffs.domain.branch.entity.Branch;
import com.ffs.web.branch.BranchResult;
import com.ffs.web.branch.request.RegisterBranchRequest;
import com.ffs.web.branch.request.UpdateBranchRequest;
import com.ffs.domain.branch.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    private ResponseEntity<Object> registerNewBranch(@RequestBody @Valid RegisterBranchRequest request) {
        Branch branch = branchService.registerBranch(request);
        BranchResult result = BranchResult.builder().branch(branch).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<Object> getAllBranch() {
        List<Branch> branchList = branchService.getAllBranch();
        BranchResult result = BranchResult.builder().branchList(branchList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getBranchById(@PathVariable Long id) {
        Branch branch = branchService.getBranchById(id);
        BranchResult result = BranchResult.builder().branch(branch).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> updateBranch(@PathVariable Long id, @RequestBody @Valid UpdateBranchRequest request) {
        Branch branch = branchService.updateBranchById(id, request);
        BranchResult branchResult = BranchResult.builder().branch(branch).build();

        return new ResponseEntity<>(branchResult, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getBranchListByGroupId(@PathVariable Long id) {
        List<Branch> branchList = branchService.getAllBranchByBranchGroupId(id);
        BranchResult result = BranchResult.builder().branchList(branchList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
