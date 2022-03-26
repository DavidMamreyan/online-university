package ru.mamreyan.onlineuniversity;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PSQLExceptionAdvice {
    @ResponseBody
    @ExceptionHandler (PSQLException.class)
    @ResponseStatus (HttpStatus.NOT_ACCEPTABLE)
    String PSQLExceptionHandler(PSQLException ex) {
        return ex.getMessage();
    }
}