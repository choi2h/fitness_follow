package com.ffs.auth.filter;

import com.ffs.auth.AuthUserProvider;
import com.ffs.auth.JwtTokenProvider;
import com.ffs.auth.PrincipalDetails;
import com.ffs.user.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HTTP_HEADER_KEY = "AT_Authorization";
    private static final String BEARER_START_VALUE = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserProvider authUserProvider;

    public JwtFilter(JwtTokenProvider jwtTokenProvider,
                     AuthUserProvider authUserProvider
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authUserProvider = authUserProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("인증이나 권한이 필요한 주소 요청됨.");
        String headerValue = request.getHeader(AUTH_HTTP_HEADER_KEY);
        String jwtToken = jwtTokenProvider.resolveToken(headerValue);

        if(jwtToken == null) {
            System.out.println("JWT token is null.");
            return;
        }

        System.out.println("=============== JWT 토큰 검증 시작 ===============");
        if(jwtTokenProvider.validateAbleToken(jwtToken)) {
            User userEntity = getUserByToken(jwtToken);
            if(userEntity == null) {
                return;
            }

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            //JWT 토큰 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("=============== JWT 토큰 검증 완료 ===============");

            filterChain.doFilter(request, response);
        }
    }

    private User getUserByToken(String token) {
        String userId = jwtTokenProvider.getPayload(token);
        String role = jwtTokenProvider.getUserType(token);

        return authUserProvider.getUser(role, userId);
    }
}
