package ru.mamreyan.onlineuniversity.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EmployeeNotValidAdvice {
    @ResponseBody
    @ExceptionHandler (EmployeeNotValidException.class)
    @ResponseStatus (HttpStatus.NOT_ACCEPTABLE)
    String employeeNotValidHandler(EmployeeNotValidException ex) {
        return ex.getMessage();
    }
}