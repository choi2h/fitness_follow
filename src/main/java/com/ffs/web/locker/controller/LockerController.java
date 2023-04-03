package com.ffs.web.locker.controller;

import com.ffs.domain.locker.entity.Locker;
import com.ffs.domain.locker.service.LockerService;
import com.ffs.web.locker.LockerResult;
import com.ffs.web.locker.request.RegisterLockerMemberRequest;
import com.ffs.web.locker.request.RegisterLockerRequest;
import com.ffs.web.locker.request.UpdateAvailableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locker")
@RequiredArgsConstructor
public class LockerController {

    private final LockerService lockerService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void registerLocker(@RequestBody RegisterLockerRequest request) {
        lockerService.registerNewLocker(request);
    }

    @PutMapping("/{branchId}/{lockerNumber}")
    public ResponseEntity<Object> registerMember
            (@PathVariable Long branchId, @PathVariable int lockerNumber, @RequestBody RegisterLockerMemberRequest request) {
        Locker locker = lockerService.registerLockerMember(branchId, lockerNumber, request);
        LockerResult result = LockerResult.builder().locker(locker).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<Object> getAllLockerByBranchId(@PathVariable Long branchId) {
        List<Locker> lockerList = lockerService.getAllLockerByBranchId(branchId);
        LockerResult result = LockerResult.builder().lockerList(lockerList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{branchId}/{lockerNumber}")
    public ResponseEntity<Object> getAllLockerByBranchId(@PathVariable Long branchId, @PathVariable int lockerNumber) {
        Locker locker = lockerService.getLocker(branchId, lockerNumber);
        LockerResult result = LockerResult.builder().locker(locker).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{branchId}/{lockerNumber}/status")
    public ResponseEntity<Object> updateLockerStatusUseOrNot
            (@PathVariable Long branchId, @PathVariable int lockerNumber, @RequestBody UpdateAvailableRequest request) {
        Locker locker = lockerService.updateLockerStatusToUseOrNot(branchId, lockerNumber, request);
        LockerResult result = LockerResult.builder().locker(locker).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
