package com.github.semiprojectshop.web.sihu.dto.oauth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class OAuthCodeParams {
    private String code;
    private String state;
    private String redirectUri;
}
