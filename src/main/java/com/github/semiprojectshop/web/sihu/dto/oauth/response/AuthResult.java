package com.github.semiprojectshop.web.sihu.dto.oauth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class AuthResult {

    private OAuthDtoInterface response;
    private boolean isConnection;
    private HttpStatus httpStatus;
    private String message;
}
