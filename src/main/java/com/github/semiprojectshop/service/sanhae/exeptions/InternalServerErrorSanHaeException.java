package com.github.semiprojectshop.service.sanhae.exeptions;

// HttpStatus.INTERNAL_SERVER_ERROR : status : 500 일 경우
public class InternalServerErrorSanHaeException extends RuntimeException {
    public InternalServerErrorSanHaeException(String message) {
        super(message);
    }
    public static InternalServerErrorSanHaeException fromMessage(String message) {
        return new InternalServerErrorSanHaeException(message);
    }
}

