package com.github.semiprojectshop.web.sanhae.advice;

import com.github.semiprojectshop.service.sanhae.exeptions.BadSanHaeException;
import com.github.semiprojectshop.service.sanhae.exeptions.ForbiddenSanHaeException;
import com.github.semiprojectshop.service.sanhae.exeptions.InternalServerErrorSanHaeException;
import com.github.semiprojectshop.service.sanhae.exeptions.UnauthorizedSanHaeException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Hidden
@RestControllerAdvice
public class ExceptionRestControllerAdviceSanHae {

    @ExceptionHandler(BadSanHaeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSanHaeBadException(BadSanHaeException e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(ForbiddenSanHaeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleSanHaeForBiddenException(ForbiddenSanHaeException e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(UnauthorizedSanHaeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleSanHaeUnauthorizedException(UnauthorizedSanHaeException e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(InternalServerErrorSanHaeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleSanHaeInternalServerException(InternalServerErrorSanHaeException e) {
        return Map.of("message", e.getMessage());
    }
}
