package com.github.semiprojectshop.web.sihu.advice;

import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
@Hidden
@RestControllerAdvice
public class ExceptionRestControllerAdvice {
    @ExceptionHandler(CustomMyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleCustomException(CustomMyException e) {
        return Map.of("message", e.getMessage());
    }



}


