package ru.mamreyan.onlineuniversity.group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GroupNotValidAdvice {
    @ResponseBody
    @ExceptionHandler (GroupNotValidException.class)
    @ResponseStatus (HttpStatus.NOT_ACCEPTABLE)
    String groupNotValidHandler(GroupNotValidException ex) {
        return ex.getMessage();
    }
}