package com.github.semiprojectshop.config.oauth.client;
import com.github.semiprojectshop.config.oauth.dto.tokens.MicrosoftTokens;
import com.github.semiprojectshop.config.oauth.dto.tokens.OAuthTokens;
import com.github.semiprojectshop.config.oauth.dto.userinfo.MicrosoftUserInfo;
import com.github.semiprojectshop.config.oauth.dto.userinfo.OAuthUserInfo;
import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import com.github.semiprojectshop.service.sihu.StorageService;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Getter(value = AccessLevel.PROTECTED)
@Component
public class MicrosoftApiClient extends OAuthApiClient{
    private final String grantType = "authorization_code";
    private final String authEndPoint = "/consumers/oauth2/v2.0/token";
    private final String apiEndPoint = "/oidc/userinfo";


    @Value("${oauth.microsoft.url.auth}")
    private String authUrl;
    @Value("${oauth.microsoft.url.api}")
    private String apiUrl;
    @Value("${oauth.microsoft.client-id}")
    private String clientId;
    @Value("${oauth.microsoft.secret}")
    private String clientSecret;

    public MicrosoftApiClient(RestTemplate restTemplate, StorageService service) {
        super(restTemplate, service);
    }

//    @Override //문서에는 get 방식으로 되어있지만 실제로는 post 방식으로 해도됨
//    protected OAuthUserInfo requestUserInfo(HttpEntity<?> request, String url) {
//        return super.getRestTemplate().exchange(url, HttpMethod.GET, request,this.getUserInfoClass()).getBody();
//    }
    //이미지 파일은 파일로 따로 요청을해야한다.

// 추후 구현        ResponseEntity<byte[]> response = restTemplate.exchange(
//                "https://graph.microsoft.com/v1.0/me/photo/$value",
//                HttpMethod.GET,
//                request,
//                byte[].class
//        );
//        byte[] imageBytes = response.getBody();
//        Path pa = storage.createFileDirectory("test", "temp");
//        String s = storage.returnTheFilePathAfterTransfer(imageBytes, pa, "내맘");
//


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.MICROSOFT;
    }


    @Override
    protected Class<? extends OAuthTokens> getTokenClass() {
        return MicrosoftTokens.class;
    }

    @Override
    protected Class<? extends OAuthUserInfo> getUserInfoClass() {
        return MicrosoftUserInfo.class;
    }
}
