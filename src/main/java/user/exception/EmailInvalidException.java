package user.exception;

public class EmailInvalidException extends RuntimeException {
    public EmailInvalidException(String email) {
        super("Email invalid: " + email);
    }
}
