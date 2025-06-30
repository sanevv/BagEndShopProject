package com.github.semiprojectshop.service.sihu.exceptions;

public class CustomMyViewException extends RuntimeException {
    private CustomMyViewException(String message) {
        super(message);
    }
    public static CustomMyViewException fromMessage(String message) {
      return new CustomMyViewException(message);
  }
}
