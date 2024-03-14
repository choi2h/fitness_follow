package com.ffs.auth;

import com.ffs.auth.filter.JwtAuthenticationFilter;
import com.ffs.auth.filter.JwtAuthorizationFilter;
import com.ffs.auth.repository.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CorsConfig corsConfig;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserProvider authUserProvider;
    private final RedisUtil redisUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(authenticationConfiguration);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider, redisUtil);
//        JwtFilter jwtFilter = new JwtFilter(jwtTokenProvider, authUserProvider);
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager, jwtTokenProvider, authUserProvider);

        return http.csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(corsConfig.corsConfigurationSource())
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilter(jwtAuthorizationFilter)
                .authorizeHttpRequests(request -> request
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/join").permitAll()
                        .antMatchers("/auth/token").permitAll()
                        .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요
                )
//                .formLogin(login -> login	// form 방식 로그인 사용
//                        .defaultSuccessUrl("/view/dashboard", true)	// 성공 시 dashboard로
//                        .permitAll()	// 대시보드 이동이 막히면 안되므로 얘는 허용
//                )
                .build();
    }
}
