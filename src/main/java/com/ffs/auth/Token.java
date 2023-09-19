package com.ffs.auth;

import lombok.*;


@Getter
public class Token {
    private String loginId;
    private String accessToken;
    private String refreshToken;

    public Token() {
    }

    public Token(String loginId, String accessToken, String refreshToken) {
        this.loginId = loginId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
