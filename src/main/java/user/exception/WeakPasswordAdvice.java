package user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class WeakPasswordAdvice {
    @ResponseBody
    @ExceptionHandler(WeakPasswordException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String weakPasswordHandler(WeakPasswordException ex) {
        return ex.getMessage();
    }
}
