package com.github.mymvcspring.service.exceptions;

public class CustomMyException extends  RuntimeException{

    private CustomMyException(String message) {
        super(message);
    }
    public static CustomMyException fromMessage(String message) {
        return new CustomMyException(message);
    }

}
