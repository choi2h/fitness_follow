package com.ffs.user.controller;

import com.ffs.user.application.UserService;
import com.ffs.user.dto.JoinRequest;
import com.ffs.user.dto.UserInfo;
import com.ffs.user.dto.UserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody @Valid JoinRequest request) {
        UserInfo userInfo = userService.join(request);
        UserResult response = UserResult.builder().user(userInfo).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
