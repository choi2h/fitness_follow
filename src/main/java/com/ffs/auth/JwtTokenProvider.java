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

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.expire-length}") long validityInMillySeconds) {
        this.key = secretKey;
        this.validityInMilliSeconds = validityInMillySeconds;
    }

    public String createToken(User user) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliSeconds);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(user.getName())
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("id", user.getLoginId())
                .claim("username", user.getName())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String getPayload(final String token) {
        return tokenToJws(token).getBody().getSubject();
    }

    public void validateAbleToken(final String token) {

        final Jws<Claims> claims;
        try {
            claims = tokenToJws(token);
        }catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }

        validateExpiredToken(claims);
    }


    private Jws<Claims> tokenToJws(final String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    private void validateExpiredToken(final Jws<Claims> claims) {
        if(claims.getBody().getExpiration().before(new Date())) {
            throw new InvalidTokenException("만료된 토큰입니다.");
        }
    }
}
