package user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class EmailInvalidAdvice {
    @ResponseBody
    @ExceptionHandler(EmailInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String emailInvalidHandler(EmailInvalidException ex) {
        return ex.getMessage();
    }
}
