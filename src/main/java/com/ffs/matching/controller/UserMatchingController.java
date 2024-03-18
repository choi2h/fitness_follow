package com.ffs.matching.controller;

import com.ffs.matching.dto.CheckMatchingResponse;
import com.ffs.matching.dto.MatchingRequest;
import com.ffs.matching.application.UserMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matching")
@RequiredArgsConstructor
public class UserMatchingController {

    private final UserMatchingService commonService;

    /**
     * 서로 매칭되어 있는 회원인지 확인
     * @return noContent
     */
    @GetMapping
    public ResponseEntity<?> checkUserAndEmployeeMatching(@RequestParam Long memberId, @RequestParam Long employeeId) {
        CheckMatchingResponse checkMatchingResponse = commonService.checkMatchingMember(memberId, employeeId);

        return ResponseEntity.ok(checkMatchingResponse);
    }

    /**
     * 회원에게 담당 직원 등록
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER')")
    public ResponseEntity<?> matchingMemberByEmployee(@RequestBody MatchingRequest matchingRequest) {
        commonService.matchingMemberAndEmployee(matchingRequest);

        return ResponseEntity.ok().build();
    }

}
