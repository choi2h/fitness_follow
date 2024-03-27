package com.ffs.user.controller;

import com.ffs.auth.PrincipalDetails;
import com.ffs.user.application.UserService;
import com.ffs.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 개인정보 수정
    @PutMapping
    public ResponseEntity<Object> updateUser
    (@AuthenticationPrincipal PrincipalDetails principalDetails, UpdateUserRequest request) {
        Long id = principalDetails.getId();
        UserInfo userInfo = userService.updateUser(id, request);
        UserResult response = UserResult.builder().user(userInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 회원 상태정보 수정
    @PutMapping("/status")
    public ResponseEntity<Object> updateUserStatus
            (@AuthenticationPrincipal PrincipalDetails principalDetails, UpdateUserStatusRequest request) {
        Long id = principalDetails.getId();
        UserInfo userInfo = userService.updateUserStatus(id, request);
        UserResult response = UserResult.builder().user(userInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
