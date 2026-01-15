package kektor.innowise.gallery.authentication.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    private static final String INVALID_REFRESH_TOKEN = "Invalid refresh token";

    public InvalidRefreshTokenException() {
        super(INVALID_REFRESH_TOKEN);
    }
}
