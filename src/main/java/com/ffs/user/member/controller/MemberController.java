package com.ffs.user.member.controller;

import com.ffs.auth.PrincipalDetails;
import com.ffs.user.member.application.MemberService;
import com.ffs.user.member.dto.MemberInfo;
import com.ffs.user.member.dto.MemberResult;
import com.ffs.user.member.dto.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Object> registerNewMember(@RequestBody @Valid RegisterMemberRequest request) {
        MemberInfo memberInfo = memberService.registerNewMember(request);
        MemberResult response = MemberResult.builder().member(memberInfo).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllMembers() {
        List<MemberInfo> memberInfos = memberService.getAllMembers();
        MemberResult response = MemberResult.builder().memberList(memberInfos).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_TRAINER')")
    @GetMapping("/me")
    public ResponseEntity<Object> getMemberById(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long id = principalDetails.getId();
        MemberInfo memberInfo = memberService.getMemberById(id);
        MemberResult response = MemberResult.builder().member(memberInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CEO, ROLE_MANAGER, ROLE_TRAINER')")
    @GetMapping("/my/members")
    public ResponseEntity<Object> getMemberListByEmployeeId(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long id = principalDetails.getId();
        List<MemberInfo> memberInfos = memberService.getMembersByEmployeeId(id);
        MemberResult response = MemberResult.builder().memberList(memberInfos).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<Object> getMemberListByBranchId(@PathParam("branch") Long branchId) {
        List<MemberInfo> memberInfos = memberService.getMembersByBranchId(branchId);
        MemberResult response = MemberResult.builder().memberList(memberInfos).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
