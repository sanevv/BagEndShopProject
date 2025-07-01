package com.github.semiprojectshop.web.sihu.dto.oauth.request;

import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@AllArgsConstructor(staticName = "of")
public class FacebookLoginParams implements OAuthLoginParams{
    private String authorizationCode;
    private String redirectUri;
    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.FACEBOOK;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("redirect_uri", redirectUri);
        return body;
    }

}
