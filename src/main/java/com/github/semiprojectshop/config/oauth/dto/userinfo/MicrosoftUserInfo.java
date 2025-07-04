package com.github.semiprojectshop.config.oauth.dto.userinfo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftUserInfo implements OAuthUserInfo {
    private String sub;
    @JsonProperty("familyname")
    private String familyName;
    @JsonProperty("givenname")
    private String givenName;
    private String email;
    private String locale;
    private String picture;

    @Override
    public String getSocialId() {
        return this.sub;
    }

    @Override
    public String getNickname() {
        return this.familyName+this.givenName;
    }

    @Override
    public String getProfileImg() {
//        return this.picture;
        return null; //위의 값은 요청보낼 api 경로이다. 따로 요청을보내서 파일을 서버에저장해야됨
        //MicrosoftApiClient 주석 메서드 참고
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.MICROSOFT;
    }
}
