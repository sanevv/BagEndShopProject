package com.github.semiprojectshop.service.sanhae.exeptions;

// UNAUTHORIZED : status : 401 일 경우
public class UnauthorizedSanHaeException extends RuntimeException {
    public UnauthorizedSanHaeException(String message) {
        super(message);
    }
    public static UnauthorizedSanHaeException fromMessage(String message) {
        return new UnauthorizedSanHaeException(message);
    }
}

