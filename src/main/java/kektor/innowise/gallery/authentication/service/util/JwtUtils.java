package kektor.innowise.gallery.authentication.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import kektor.innowise.gallery.authentication.config.JwtConfigProperties;
import kektor.innowise.gallery.authentication.dto.UserDto;
import kektor.innowise.gallery.authentication.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    final JwtConfigProperties jwtConfig;

    public Claims parseAccessToken(String accessToken) throws JwtException {
        return Jwts.parser()
                .verifyWith(jwtConfig.getKeyPair().getPublic())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    public String generateAccessToken(UserDto user) {
        return Jwts.builder()
                .issuer("auth-service")
                .subject(user.username())
                .id(generateJti())
                .claim("user_id", user.id())
                .claim("email", user.email())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessExpiration().toMillis()))
                .signWith(jwtConfig.getKeyPair().getPrivate())
                .compact();
    }

    public RefreshToken createRefreshToken(UserDto user) {
        return RefreshToken.builder()
                .token(generateRefreshToken())
                .userId(user.id())
                .expiryDate(Instant.now().plus(jwtConfig.getRefreshExpiration()))
                .build();
    }

    public Duration getRemainingTime(Claims claims) {
        Date expiration = claims.getExpiration();
        long expirationTime = expiration.getTime();
        long remainingSec = (expirationTime - System.currentTimeMillis()) / 1000;
        return remainingSec > 0 ? Duration.ofSeconds(remainingSec) : Duration.ZERO;
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public String generateJti() {
        return UUID.randomUUID().toString();
    }

    public int sessionCountLimit() {
        return jwtConfig.getActiveSessionLimit();
    }
}
