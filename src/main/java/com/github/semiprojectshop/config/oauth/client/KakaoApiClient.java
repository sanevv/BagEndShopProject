package com.github.semiprojectshop.config.oauth.client;
import com.github.semiprojectshop.config.oauth.dto.tokens.KakaoTokens;
import com.github.semiprojectshop.config.oauth.dto.userinfo.KakaoUserInfo;
import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import com.github.semiprojectshop.service.sihu.StorageService;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Getter(value = AccessLevel.PROTECTED)
@Component
public class KakaoApiClient extends OAuthApiClient {

    private final String grantType = "authorization_code";
    private final String authEndPoint = "/oauth/token";
    private final String apiEndPoint = "/v2/user/me";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.secret}")
    private String clientSecret;

    public KakaoApiClient(RestTemplate restTemplate, StorageService service) {
        super(restTemplate, service);
    }


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }



    @Override
    protected Class<KakaoTokens> getTokenClass() {
        return KakaoTokens.class;
    }

    @Override
    protected Class<KakaoUserInfo> getUserInfoClass() {
        return KakaoUserInfo.class;
    }
}
