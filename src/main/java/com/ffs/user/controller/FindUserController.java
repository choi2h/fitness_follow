package com.ffs.user.controller;

import com.ffs.auth.PrincipalDetails;
import com.ffs.user.application.FindUserService;
import com.ffs.user.dto.UserInfo;
import com.ffs.user.dto.UserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class FindUserController {

    private final FindUserService findUserService;

    // 회원 자신의 개인 정보 조회
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/me")
    public ResponseEntity<Object> getMemberById(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long id = principalDetails.getId();
        UserInfo memberInfo = findUserService.findUserById(id);
        UserResult response = UserResult.builder().user(memberInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 회원ID로 회원 정보 조회
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_TRAINER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMemberById(@PathVariable("id") Long id) {
        UserInfo userInfo = findUserService.findUserById(id);
        UserResult response = UserResult.builder().user(userInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 지점에 등록되어 있는 직원 정보 조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{branch_id}/employee")
    public ResponseEntity<Object> getAllEmployeeUsersByBranchId(@PathVariable("branch_id")Long branchId) {
        List<UserInfo> userInfoList = findUserService.findEmployeeUsersByBranchId(branchId);
        UserResult response = UserResult.builder().userList(userInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 지점에 등록되어 있는 회원 정보 조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{branch_id}/member")
    public ResponseEntity<Object> getAllMemberUsersByBranchId(@PathVariable("branch_id")Long branchId) {
        List<UserInfo> userInfoList = findUserService.findMemberUsersByBranchId(branchId);
        UserResult response = UserResult.builder().userList(userInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 매칭되어 있는 회원정보 조회
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER, ROLE_TRAINER')")
    @GetMapping("/matching")
    public ResponseEntity<Object> getAllMemberUsersByEmployee(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<UserInfo> userInfoList = findUserService.findMemberUsersByEmployeeId(principalDetails.getId());
        UserResult response = UserResult.builder().userList(userInfoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
