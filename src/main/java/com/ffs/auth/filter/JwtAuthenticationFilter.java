package com.ffs.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffs.auth.AuthUser;
import com.ffs.auth.JwtTokenProvider;
import com.ffs.auth.PrincipalDetails;
import com.ffs.auth.Token;
import com.ffs.auth.repository.redis.RedisUtil;
import com.ffs.user.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("JwtAuthenticationFilter : 로그인 시도중");

        LoginRequest loginRequest =  null;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            log.debug("Login request. id={}",  loginRequest.getId());
        } catch (IOException e) {
            log.error("Can not convert login request.", e);
        }

        if(loginRequest == null) {
            return null;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword());
        log.debug("Token={}", authenticationToken);

        log.debug("authenticationManager : {}", authenticationManager);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        log.debug("principal : {}", authentication.getPrincipal());
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.debug("로그인 완료됨 username={}", principalDetails.getUsername()); //로그인이 정상적으로 되었다는 뜻.

        return authentication;
    }

    @Transactional
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        log.debug("successfulAuthentication 실행-인증완료");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        AuthUser user = principalDetails.getAuthUser();

        Token jwtToken = jwtTokenProvider.createToken(user);
        response.addHeader("AT_Authorization", "Bearer "+jwtToken.getAccessToken());
        response.addHeader("RT_Authorization", "Bearer "+jwtToken.getRefreshToken());
        log.debug("Generate access token. AT_Authorization={}", jwtToken.getAccessToken());
        log.debug("Generate refresh token. RT_Authorization={}", jwtToken.getRefreshToken());

        String userId = user.getLoginId();
        redisUtil.set(userId, jwtToken, 30000);

        log.debug("accessToken={}", jwtToken.getAccessToken());
        log.debug("refreshToken={}", jwtToken.getRefreshToken());
        redisUtil.set(userId, jwtToken, 30000);
    }
}
