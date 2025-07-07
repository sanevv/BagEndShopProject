package com.github.semiprojectshop.service.sanhae.exeptions;

// FORBIDDEN : status : 403 일 경우
public class ForbiddenSanHaeException extends RuntimeException {
    public ForbiddenSanHaeException(String message) {
        super(message);
    }
    public static ForbiddenSanHaeException fromMessage(String message) {
        return new ForbiddenSanHaeException(message);
    }
}

