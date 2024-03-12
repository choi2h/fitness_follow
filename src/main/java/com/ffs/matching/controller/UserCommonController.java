package com.ffs.matching.controller;

import com.ffs.matching.dto.CheckMatchingResponse;
import com.ffs.matching.dto.MatchingRequest;
import com.ffs.matching.application.UserMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matching")
@RequiredArgsConstructor
public class UserCommonController {

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
     * 직원-회원 매칭(담당자 지정)
     */
    @PostMapping
    public ResponseEntity<?> matchingMemberByEmployee(@RequestBody MatchingRequest matchingRequest) {
        commonService.matchingMemberAndEmployee(matchingRequest);

        return ResponseEntity.ok().build();
    }

}
