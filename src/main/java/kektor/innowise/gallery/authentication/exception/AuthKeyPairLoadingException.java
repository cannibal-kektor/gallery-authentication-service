package kektor.innowise.gallery.authentication.exception;

public class AuthKeyPairLoadingException extends RuntimeException {

    public AuthKeyPairLoadingException(String message, Exception e) {
        super(message, e);
    }

}
