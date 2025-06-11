package com.github.mymvcspring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginResponse {
    private String userId;
    private String message;
    private boolean success;
    private boolean changedPassword;
    public static LoginResponse of(String userId, String message, boolean success) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.userId = userId;
        loginResponse.message = message;
        loginResponse.success = success;
        return loginResponse;
    }
    public LoginResponse needToChangePassword(){
        this.changedPassword = true;
        return this;
    }
}
