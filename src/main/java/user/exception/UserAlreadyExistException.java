package user.exception;

import user.User;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(User user) {
        super("User already exist: " + user);
    }
}
