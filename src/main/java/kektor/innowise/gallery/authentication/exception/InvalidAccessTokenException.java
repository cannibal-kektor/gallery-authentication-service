package kektor.innowise.gallery.authentication.exception;


import io.jsonwebtoken.JwtException;

public class InvalidAccessTokenException extends RuntimeException {

    private static final String INVALID_ACCESS_TOKEN = "Invalid access token";

    public InvalidAccessTokenException(JwtException e) {
        super(INVALID_ACCESS_TOKEN, e);
    }
}
