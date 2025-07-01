package com.github.semiprojectshop.web.sihu.dto.oauth.request;

import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@AllArgsConstructor(staticName = "of")
public class TwitterLoginParams implements OAuthLoginParams {
    private String authorizationCode;
    private String state;
    private String redirectUri;



    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.TWITTER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("redirect_uri", redirectUri);
        body.add("code_verifier", "challenge");
        return body;
    }

}
