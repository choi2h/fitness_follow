package com.ffs.user.member.controller;

import com.ffs.user.member.application.MemberService;
import com.ffs.user.member.dto.MemberInfo;
import com.ffs.user.member.dto.MemberResult;
import com.ffs.user.member.dto.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<Object> getAllMembers() {
        List<MemberInfo> memberInfoList = memberService.getAllMembers();
        MemberResult response = MemberResult.builder().memberList(memberInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_TRAINER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMemberById(@PathVariable Long id) {
        MemberInfo memberInfo = memberService.getMemberById(id);
        MemberResult response = MemberResult.builder().member(memberInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getMemberListByBranchId(@PathVariable Long id) {
        List<MemberInfo> memberList = memberService.getMembersByBranchId(id);
        MemberResult response = MemberResult.builder().memberList(memberList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
