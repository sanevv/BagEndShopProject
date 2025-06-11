package com.github.mymvcspring.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwChangeRequest {
    private String userId;
    private String password;
    private String passwordConfirm;
}
