package kektor.innowise.gallery.authentication.exception;

public class TooManyActiveSessionsException extends RuntimeException {

    private static final String TOO_MANY_ACTIVE_SESSIONS = "Too many active sessions";

    public TooManyActiveSessionsException() {
        super(TOO_MANY_ACTIVE_SESSIONS);
    }
}
