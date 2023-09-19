package com.ffs.auth;

import com.ffs.auth.exception.InvalidTokenException;
import com.ffs.user.User;
import com.ffs.user.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private static final String JWT_SECRET_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".repeat(2);
    private static final long JWT_ACCESS_TOKEN_VALID_LENGTH = 3000;
    private static final long JWT_ACCESS_TOKEN_EXPIRE_LENGTH = 5000;
    private static final User user = getMember();

    private final JwtTokenProvider jwtTokenProvider =
            new JwtTokenProvider(JWT_SECRET_KEY, JWT_ACCESS_TOKEN_VALID_LENGTH, JWT_ACCESS_TOKEN_EXPIRE_LENGTH);

    private static Member getMember() {
        return  Member
                .builder()
                .name("최이화")
                .address("서울시 송파구")
                .phoneNumber("010-0000-0000")
                .loginId("qwe")
                .password("123")
                .status("일반회원")
                .build();
    }

    @DisplayName("AccessToken을 생성한다.")
    @Test
    void createTokenTest() {
        final Token token = jwtTokenProvider.createToken(user);

        System.out.println("access token: " + token.getAccessToken());
        System.out.println("refresh token: " + token.getRefreshToken());

        boolean isVaild = jwtTokenProvider.validateAbleToken(token.getAccessToken());
        assertNotNull(token);
        assertTrue(isVaild);
    }

    @DisplayName("올바른 토큰 정보로 payload를 조회한다.")
    @Test
    void getPayloadByValidToken() {
        final Token token = jwtTokenProvider.createToken(user);
        final String payload = jwtTokenProvider.getPayload(token.getAccessToken());

        assertEquals(payload, user.getName());
    }

    @DisplayName("유효기간이 만기된 토큰으로 payload를 조회할 경우 예외를 발생시킨다.")
    @Test
    void getPayloadByInvalidToken() throws InterruptedException {
        final Token token = jwtTokenProvider.createToken(user);
        Thread.sleep(JWT_ACCESS_TOKEN_EXPIRE_LENGTH);

        assertThrows(InvalidTokenException.class, () -> jwtTokenProvider.validateAbleToken(token.getAccessToken()));
    }
}