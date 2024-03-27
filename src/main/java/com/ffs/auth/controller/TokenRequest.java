package com.ffs.auth.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRequest {

    private String accessToken;
    private String refreshToken;

}
