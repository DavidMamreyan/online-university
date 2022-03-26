package ru.mamreyan.onlineuniversity.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class StudentNotValidAdvice {
    @ResponseBody
    @ExceptionHandler (StudentNotValidException.class)
    @ResponseStatus (HttpStatus.NOT_ACCEPTABLE)
    String studentNotValidHandler(StudentNotValidException ex) {
        return ex.getMessage();
    }
}