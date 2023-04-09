package com.ffs.web.member.controller;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.member.Member;
import com.ffs.domain.member.service.MemberService;
import com.ffs.web.member.MemberResult;
import com.ffs.web.member.request.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Object> registerNewMember(@RequestBody @Valid RegisterMemberRequest request) {
        Member member = memberService.registerNewMember(request);
        MemberResult response = MemberResult.builder().member(member).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllMembers() {
        List<Member> memberList;

        try {
            memberList = memberService.getAllMember();
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        MemberResult response = MemberResult.builder().memberList(memberList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMemberById(@PathVariable Long id) {
        Member member;

        try {
            member = memberService.getMemberById(id);
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        MemberResult response = MemberResult.builder().member(member).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getMemberListByBranchId(@PathVariable Long id) {
        List<Member> memberList;

        try{
            memberList = memberService.getMemberListByBranchId(id);
        } catch (ServiceResultCodeException e) {
            throw e;
        }

        MemberResult response = MemberResult.builder().memberList(memberList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
