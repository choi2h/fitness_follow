package com.ffs.auth;

import com.ffs.auth.exception.InvalidTokenException;
import com.ffs.user.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String key;

    private final long validityInMilliSeconds;

    private final long expireMilliSeconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.valid-length}") long validityInMillySeconds,
                            @Value("${security.jwt.token.expire-length}") long expireMilliSeconds) {
        this.key = secretKey;
        this.validityInMilliSeconds = validityInMillySeconds;
        this.expireMilliSeconds = expireMilliSeconds;
    }

    public Token createToken(User user) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliSeconds);
        final Date expiration = new Date(now.getTime() + expireMilliSeconds);

        Claims claims = Jwts.claims().setSubject(user.getLoginId());
        claims.put("username", user.getName());
        claims.put("role", user.getRole());

        String accessToken = generateAccessToken(user, now, validity, claims);
        String refreshToken = generateRefreshToken(now, expiration, claims);

        return new Token(user.getLoginId(), accessToken, refreshToken);
    }

    private String generateRefreshToken(Date now, Date expiration, Claims claims) {
        claims.setExpiration(expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256,  key)
                .compact();
    }

    private String generateAccessToken(User user, Date now, Date validity, Claims claims) {
        claims.setExpiration(validity);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(user.getName())
                .setIssuedAt(now)
                .setExpiration(validity)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String getPayload(final String token) {
        return tokenToJws(token).getBody().getSubject();
    }

    public String getUserType(final String token) {
        return String.valueOf(tokenToJws(token).getBody().get("role"));

    }

    public boolean validateAbleToken(final String token) {

        final Jws<Claims> claims;
        try {
            claims = tokenToJws(token);
        }catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }

        System.out.println("claims = " + claims);
        return validateExpiredToken(claims);
    }

    public String resolveToken(String token) {
        if(token != null && token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "");
        }

        return null;
    }

    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody().getExpiration();
        // 현재 시간
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    private Jws<Claims> tokenToJws(final String token) {
        System.out.println("token to jws=" + token);
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    private boolean validateExpiredToken(final Jws<Claims> claims) {
        Date expiration = claims.getBody().getExpiration();
        Date now = new Date();
        System.out.println("expiration=" + expiration + "now=" + now);

        if(expiration.before(now)) {
            throw new InvalidTokenException("만료된 토큰입니다.");
        }

        return true;
    }
}
