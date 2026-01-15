package kektor.innowise.gallery.authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import kektor.innowise.gallery.authentication.dto.LoginRequest;
import kektor.innowise.gallery.authentication.dto.LogoutRequest;
import kektor.innowise.gallery.authentication.dto.RefreshRequest;
import kektor.innowise.gallery.authentication.dto.TokenResponse;
import kektor.innowise.gallery.authentication.dto.UserDto;
import kektor.innowise.gallery.authentication.dto.ValidateRequest;
import kektor.innowise.gallery.authentication.exception.InvalidAccessTokenException;
import kektor.innowise.gallery.authentication.exception.InvalidRefreshTokenException;
import kektor.innowise.gallery.authentication.exception.TokenExpiredException;
import kektor.innowise.gallery.authentication.exception.TooManyActiveSessionsException;
import kektor.innowise.gallery.authentication.exception.UserNotFoundException;
import kektor.innowise.gallery.authentication.exception.UsernameNotFoundException;
import kektor.innowise.gallery.authentication.model.RefreshToken;
import kektor.innowise.gallery.authentication.repository.RefreshTokenRepository;
import kektor.innowise.gallery.authentication.service.util.JwtUtils;
import kektor.innowise.gallery.authentication.webclient.UserServiceClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenService {

    final RefreshTokenRepository tokenRepository;
    final PasswordService passwordService;
    final UserServiceClient userService;
    final StringRedisTemplate redisTemplate;
    final JwtUtils jwtUtils;

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        UserDto user = userService.fetchUser(loginRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequest.username()));
        passwordService.validateUserPassword(user.id(), loginRequest.password());

        checkNumOfActiveSessions(user.id());
        String accessToken = jwtUtils.generateAccessToken(user);
        RefreshToken refreshToken = jwtUtils.createRefreshToken(user);

        refreshToken = tokenRepository.save(refreshToken);
        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    @Transactional
    public TokenResponse refresh(RefreshRequest refreshRequest) {
        RefreshToken storedRefreshToken = validateRefreshToken(refreshRequest.refreshToken());
        UserDto user = userService.fetchUser(storedRefreshToken.getUserId())
                .orElseThrow(() -> new UserNotFoundException(storedRefreshToken.getUserId()));

        String newAccessToken = jwtUtils.generateAccessToken(user);
        RefreshToken newRefreshRefreshToken = jwtUtils.createRefreshToken(user);

        tokenRepository.delete(storedRefreshToken);
        newRefreshRefreshToken = tokenRepository.save(newRefreshRefreshToken);
        return new TokenResponse(newAccessToken, newRefreshRefreshToken.getToken());
    }

    public boolean validate(ValidateRequest validateRequest) {
        try {
            jwtUtils.parseAccessToken(validateRequest.accessToken());
        } catch (JwtException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public void logout(LogoutRequest logoutRequest) {
        Claims claims;
        try {
            claims = jwtUtils.parseAccessToken(logoutRequest.accessToken());
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e);
        }
        tokenRepository.deleteByToken(logoutRequest.refreshToken());

        Duration ttl = jwtUtils.getRemainingTime(claims);
        redisTemplate.opsForValue().set("blacklist:" + claims.getId(), "revoked", ttl);
    }

    public RefreshToken validateRefreshToken(String refreshToken) {
        RefreshToken storedRefreshToken = tokenRepository.findByToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);
        if (storedRefreshToken.getExpiryDate().isBefore(Instant.now())) {
            tokenRepository.deleteById(storedRefreshToken.getId());
            throw new TokenExpiredException(storedRefreshToken.getExpiryDate());
        }
        return storedRefreshToken;
    }

    private void checkNumOfActiveSessions(Long userId) {
        if (tokenRepository.countAllByUserId(userId) > jwtUtils.sessionCountLimit())
            throw new TooManyActiveSessionsException();
    }

    @Transactional
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void deleteExpiredTokens() {
        tokenRepository.deleteAllByExpiryDateBefore(Instant.now());
    }

}
