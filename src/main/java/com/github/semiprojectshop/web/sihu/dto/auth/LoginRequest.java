package com.github.semiprojectshop.web.sihu.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userId;
    private String password;
    private boolean rememberMe;
}
