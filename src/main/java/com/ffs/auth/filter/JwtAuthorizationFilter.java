package com.ffs.auth.filter;

import com.ffs.auth.AuthUser;
import com.ffs.auth.AuthUserProvider;
import com.ffs.auth.JwtTokenProvider;
import com.ffs.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 시큐리티가 filter를 가지고 있는데 그 필터 중 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소 요청 시 이 필터를 무조건 타게 되어있음.
// 권한이나 인증이 필요한 주소가 아니면 이 필터를 안 탐.
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String AUTH_HTTP_HEADER_KEY = "AT_Authorization";
    private static final String REFRESH_AUTH_HTTP_HEADER_KEY = "RT_Authorization";
    private static final String BEARER_START_VALUE = "Bearer ";

    private JwtTokenProvider jwtTokenProvider;
    private AuthUserProvider authUserProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtTokenProvider jwtTokenProvider, AuthUserProvider authUserProvider){
        super(authenticationManager);
        this.authUserProvider = authUserProvider;
        this. jwtTokenProvider = jwtTokenProvider;
    }

    //인증이나 권한이 필요한 주소 요청 시 해당 필터를 타게됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);
        System.out.println("인증이나 권한이 필요한 주소 요청됨.");

        String jwtHeader = request.getHeader(AUTH_HTTP_HEADER_KEY);
        System.out.println("jwtHeader: " + jwtHeader);

        // 정상 헤더가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith(BEARER_START_VALUE)) {
            chain.doFilter(request, response);
            return;
        }

        System.out.println("=============== JWT 토큰 검증 시작 ===============");
        System.out.println("token : " + jwtHeader);
        String token = jwtHeader.replace("Bearer ", "");
        if(jwtTokenProvider.validateAbleToken(token)) {
            String userId = jwtTokenProvider.getPayload(token);

            System.out.println("loginId=" + userId);
            AuthUser authUser = authUserProvider.getAuthUser(userId);

            PrincipalDetails principalDetails = new PrincipalDetails(authUser);
            //JWT 토큰 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("=============== JWT 토큰 검증 완료 ===============");

            chain.doFilter(request, response);
        }
    }
}

