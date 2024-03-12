package com.ffs.user.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matching")
@RequiredArgsConstructor
public class UserCommonController {

    private final UserCommonService commonService;

    /**
     * 직원의 담당 회원인지 확인
     * @return noContent
     */
    @GetMapping
    public ResponseEntity<?> checkUserAndEmployeeMatching(@RequestParam Long memberId, @RequestParam Long employeeId) {
        commonService.checkMatchingMember(memberId, employeeId);

        return ResponseEntity.noContent().build();
    }

    /**
     * 직원에게 담당 회원 배정
     */
    @PostMapping
    public ResponseEntity<?> matchingMemberByEmployee(@RequestBody MatchingRequest matchingRequest) {
        commonService.matchingMemberAndEmployee(matchingRequest);

        return ResponseEntity.ok().build();
    }

}
