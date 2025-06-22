package com.github.semiprojectshop.service.sihu.exceptions;

public class CustomMyException extends  RuntimeException{

    private CustomMyException(String message) {
        super(message);
    }
    public static CustomMyException fromMessage(String message) {
        return new CustomMyException(message);
    }

}
