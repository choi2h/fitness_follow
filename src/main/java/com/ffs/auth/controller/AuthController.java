package com.ffs.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ffs.auth.application.AuthService;
import com.ffs.auth.controller.dto.LogoutRequest;
import com.ffs.common.DefaultResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<Object> generateAccessAndRefreshToken(
            @Valid @RequestBody TokenRequest tokenRequest) throws JsonProcessingException {
        log.info("Receive request for refresh token.");
        String accessToken = authService.refreshToken(tokenRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("AT_Authorization", "Bearer "+accessToken);
        headers.add("RT_Authorization", tokenRequest.getRefreshToken());


        return ResponseEntity.ok().headers(headers).body(DefaultResultCode.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@Valid @RequestBody LogoutRequest logoutRequest) throws JsonProcessingException {
        authService.logout(logoutRequest);

        return ResponseEntity.ok().body(DefaultResultCode.OK);
    }
}
