package com.github.mymvcspring.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userId;
    private String password;
}
