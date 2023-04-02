package com.ffs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffs.dao.Branch;
import com.ffs.dto.BranchResult;
import com.ffs.dto.RegisterBranchRequest;
import com.ffs.dto.UpdateBranchRequest;
import com.ffs.service.BranchService;
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
        Long id = branchService.registerBranch(request);
        BranchResult result = BranchResult.builder().id(id).build();

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
    private ResponseEntity<Object> updateBranch(@PathVariable Long id, @RequestBody UpdateBranchRequest request) {
        Long result = branchService.updateBranchById(id, request);
        BranchResult branchResult = BranchResult.builder().id(result).build();

        return new ResponseEntity<>(branchResult, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getBranchListByGroupId(@PathVariable Long id) {
        List<Branch> branchList = branchService.getAllBranchByBranchGroupId(id);
        BranchResult result = BranchResult.builder().branchList(branchList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
