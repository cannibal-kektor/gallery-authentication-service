package kektor.innowise.gallery.authentication.exception;

public class InvalidPasswordException extends RuntimeException {

    private static final String INVALID_PASSWORD = "Invalid password";

    public InvalidPasswordException() {
        super(INVALID_PASSWORD);
    }
}
