package co.uk.cbradbury.quackstats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The request was invalid")
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}