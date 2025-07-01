package com.github.semiprojectshop.config.oauth.dto.userinfo;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface OAuthUserInfo  {
    String getSocialId();
    String getEmail();
    String getNickname();
    String getProfileImg();
    OAuthProvider getOAuthProvider();

    default OAuthUserInfo updateEmailReturnThis(String email) {
        throw new UnsupportedOperationException("This method is not supported.");
    }
}
