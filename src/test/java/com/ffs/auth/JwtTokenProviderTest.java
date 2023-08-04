package com.ffs.auth;

import com.ffs.auth.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private static final String JWT_SECRET_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".repeat(2);
    private static final long JWT_ACCESS_TOKEN_EXPIRE_LENGTH = 3000;
    private static final String PAYLOAD = "payload";

    private final JwtTokenProvider jwtTokenProvider =
            new JwtTokenProvider(JWT_SECRET_KEY, JWT_ACCESS_TOKEN_EXPIRE_LENGTH);

    @DisplayName("AccessToken을 생성한다.")
    @Test
    void createTokenTest() {
        final String token = jwtTokenProvider.createToken(PAYLOAD);

        System.out.println("token: " + token);
        assertNotNull(token);
    }

    @DisplayName("올바른 토큰 정보로 payload를 조회한다.")
    @Test
    void getPayloadByValidToken() {
        final String token = jwtTokenProvider.createToken(PAYLOAD);
        final String payload = jwtTokenProvider.getPayload(token);

        assertEquals(payload, PAYLOAD);
    }

    @DisplayName("유효기간이 만기된 토큰으로 payload를 조회할 경우 예외를 발생시킨다.")
    @Test
    void getPayloadByInvalidToken() throws InterruptedException {
        final String token = jwtTokenProvider.createToken(PAYLOAD);
        Thread.sleep(JWT_ACCESS_TOKEN_EXPIRE_LENGTH);

        assertThrows(InvalidTokenException.class, () -> jwtTokenProvider.validateAbleToken(token));

    }
}