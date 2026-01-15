package kektor.innowise.gallery.authentication.exception;

public class PasswordNotFoundException extends RuntimeException {

    private static final String PASSWORD_NOT_FOUND = "Password for user with id: (%d) not found";

    public PasswordNotFoundException(Long userId) {
        super(String.format(PASSWORD_NOT_FOUND, userId));
    }
}
