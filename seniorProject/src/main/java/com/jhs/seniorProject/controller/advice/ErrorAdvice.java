package com.jhs.seniorProject.controller.advice;

import com.jhs.seniorProject.domain.exception.DuplicateFriendException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> BindExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> error = new HashMap<>();
        error.put("error", bindingResult.getFieldError().getDefaultMessage());
        return error;
    }

    @ExceptionHandler({NoSuchUserException.class, DuplicateFriendException.class})
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> UserDefinedExceptionHandler(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return error;
    }

}
