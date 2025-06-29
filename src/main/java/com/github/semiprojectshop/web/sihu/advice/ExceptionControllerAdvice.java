package com.github.semiprojectshop.web.sihu.advice;

import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyViewException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Hidden
@ControllerAdvice//Rest 가아닌 jsp view 를 반환해야하니 ControllerAdvice 사용
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler(CustomMyViewException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(CustomMyViewException e) {
        // Log the exception or handle it as needed
        log.error(e.getMessage(), e);
        return "error"; // Return the name of the error page view
    }
}
