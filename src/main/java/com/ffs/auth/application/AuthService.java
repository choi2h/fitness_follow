package com.ffs.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ffs.auth.AuthUserProvider;
import com.ffs.auth.JwtTokenProvider;
import com.ffs.auth.PrincipalDetails;
import com.ffs.auth.Token;
import com.ffs.auth.controller.TokenRequest;
import com.ffs.auth.controller.dto.LogoutRequest;
import com.ffs.auth.exception.InvalidTokenException;
import com.ffs.auth.repository.redis.RedisUtil;
import com.ffs.user.User;
import com.ffs.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserProvider authUserProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final JsonUtil jsonUtil;

    public String refreshToken(TokenRequest tokenRequest) throws JsonProcessingException {
        String accessToken = tokenRequest.getAccessToken();
        String refreshToken = tokenRequest.getRefreshToken();

        log.debug("accessToken={}", accessToken);
        log.debug("refreshToken={}", refreshToken);

        String userId = jwtTokenProvider.getPayload(accessToken);

        Object obj = redisUtil.get(userId);
        if(obj == null) {
            throw new InvalidTokenException();
        }

        String string = String.valueOf(obj);
        Token token = jsonUtil.jsonToObject(string, Token.class);
        log.debug("Found token : {}", token);

        if(!refreshToken.equals(token.getRefreshToken())) {
            throw new InvalidTokenException();
        }

        jwtTokenProvider.validateAbleToken(token.getRefreshToken());
        String role = jwtTokenProvider.getUserType(accessToken);
        User user = authUserProvider.getUser(role, userId);

        Token newToken = jwtTokenProvider.createToken(user);
        newToken.setRefreshToken("Bearer " + refreshToken);
        setAuthentication(user);
        log.debug("accessToken={}", newToken.getAccessToken());
        log.debug("refreshToken={}", newToken.getRefreshToken());
        redisUtil.set(userId, newToken, 30000);

        return newToken.getAccessToken();
    }

    private void setAuthentication(User userEntity) {
        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
        //JWT 토큰 서명이 정상이면 Authentication 객체를 만들어준다.
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        // 강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void logout(LogoutRequest logoutRequest) throws JsonProcessingException {
        String acToken = logoutRequest.getAccessToken().replace("Bearer ", "");
        log.debug("accessToken = {}", acToken );

        // 1. Access Token 검증
        jwtTokenProvider.validateAbleToken(acToken);

        // 2. Access Token 에서 User email 을 가져옵니다.
        String loginId = jwtTokenProvider.getPayload(acToken);

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisUtil.get(loginId) != null) {
            // Refresh Token 삭제
            redisUtil.delete(loginId);
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logoutRequest.getAccessToken());
        redisUtil.setBlackList(loginId, "logout", expiration);

    }
}
