package kektor.innowise.gallery.authentication.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class TokenExpiredException extends RuntimeException {

    private static final String TOKEN_EXPIRED = "Refresh token expired at %s";

    private final Instant expiredAt;

    public TokenExpiredException(Instant instant) {
        super(String.format(TOKEN_EXPIRED, instant.toString()));
        this.expiredAt = instant;
    }
}
