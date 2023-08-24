package com.ffs.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffs.auth.AuthUserProvider;
import com.ffs.auth.JwtTokenProvider;
import com.ffs.auth.PrincipalDetails;
import com.ffs.user.User;
import com.ffs.user.member.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserProvider authUserProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
//                                   JwtTokenProvider jwtTokenProvider, AuthUserProvider authUserProvider) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.authUserProvider = authUserProvider;
//
//    }

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("JwtAuthenticationFilter : 로그인 시도중");

        LoginRequest loginRequest =  null;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            log.debug("Login request. type={}, id={}", loginRequest.getType(), loginRequest.getId());
        } catch (IOException e) {
            log.error("Can not convert login request.", e);
        }

        if(loginRequest == null) {
            return null;
        }

        User user = authUserProvider.getUser(loginRequest);
        if(user == null) {
            return null;
        }

        log.debug("User role={}", user.getRole());

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

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("successfulAuthentication 실행-인증완료");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = jwtTokenProvider.createToken(principalDetails.getUser());
        response.addHeader("Authorization", "Bearer "+jwtToken);
    }
}
