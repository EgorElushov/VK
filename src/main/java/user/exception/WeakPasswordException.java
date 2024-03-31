package user.exception;

public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException(String password) {
        super("Weak password: " + password);
    }
}
