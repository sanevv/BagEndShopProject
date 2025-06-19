package com.github.semiprojectshop.web.sihu.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwChangeRequest {
    private String userId;
    private String password;
    private String passwordConfirm;
}
