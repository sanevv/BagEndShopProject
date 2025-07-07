package com.github.semiprojectshop.service.sanhae.exeptions;

// BAD REQUEST : status : 400 일 경우
public class BadSanHaeException extends RuntimeException {
    public BadSanHaeException(String message) {
        super(message);
    }
    public static BadSanHaeException fromMessage(String message) {
        return new BadSanHaeException(message);
    }
}

