package com.github.semiprojectshop.service.sihu.oauth;

import com.github.semiprojectshop.config.server.ServerUrlFields;
import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import jakarta.servlet.http.HttpServletRequest;
import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OAuthProviderService {
    private final OAuthCodeManager oAuthCodeManager;
    private final Function<ServerUrlFields, String> requestBaseUrlProvider;


    public String getOAuthLoginPageUrl(OAuthProvider oAuthProvider, HttpServletRequest httpServletRequest) {
        String baseUrl = getRequestBaseUrl(httpServletRequest);
        String redirectUrl = baseUrl + "/api/oauth/" +oAuthProvider.name().toLowerCase()+"/callback";
        return oAuthCodeManager.getAuthorizationUrl(oAuthProvider, redirectUrl);
    }

    //로컬의 jsp view controller의 redirectUrl을 반환 하기위한 메서드
    public String getOAuthLoginPageUrlLocal(OAuthProvider oAuthProvider, HttpServletRequest httpServletRequest) {
        String baseUrl = getRequestBaseUrl(httpServletRequest);
        String redirectUrl = baseUrl + "/oauth/" +oAuthProvider.name().toLowerCase()+"/callback";
        return oAuthCodeManager.getAuthorizationUrl(oAuthProvider, redirectUrl);
    }

    /**
     * OAuth 로그인 페이지 URL을 생성합니다.
     *
     * @param oAuthProvider OAuth 제공자
     * @param redirectUrl   리다이렉트 URL
     * @return OAuth 로그인 페이지 URL
     */
    public String getOAuthLoginPageUrl(OAuthProvider oAuthProvider, String redirectUrl) {
        return oAuthCodeManager.getAuthorizationUrl(oAuthProvider, redirectUrl);
    }

    private String getRequestBaseUrl(HttpServletRequest request) {
        ServerUrlFields fields = ServerUrlFields.fromRequest(request);
        return requestBaseUrlProvider.apply(fields);
    }
}
