package com.github.semiprojectshop.web.sihu.restcontroller;

import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import com.github.semiprojectshop.service.sihu.oauth.OAuthLoginService;
import com.github.semiprojectshop.service.sihu.oauth.OAuthProviderService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.*;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.AuthResult;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.OAuthDtoInterface;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.OAuthSignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OauthRestController {
    private final OAuthProviderService oAuthProviderService;
    private final OAuthLoginService oAuthLoginService;

    @GetMapping("/{provider}/test")//테스트용 oAuthRequest 로 리다이렉션됨
    public ResponseEntity<Void> requestOAuthCodeUrlRedirect(@PathVariable OAuthProvider provider, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, oAuthProviderService.getOAuthLoginPageUrl(provider, httpServletRequest))
                .build();
    }

    @GetMapping("/{provider}/callback")//백에서 Post 요청 api 메서드 호출해서 처리
    public ResponseEntity<CustomResponse<OAuthDtoInterface>> oAuthRequest(@PathVariable OAuthProvider provider, HttpServletRequest request) {

        OAuthCodeParams codeParams = OAuthCodeParams.of(
                request.getParameter("code"),
                request.getParameter("state"),
                request.getRequestURL().toString()
        );

        return loginOAuth(OAuthLoginParams.fromCodeParams(codeParams, provider));
    }// 여기까지는 백에서만 처리를하는 테스트 메서드



    @GetMapping("/{provider}")
    public CustomResponse<String> getProviderAuthUrl(@PathVariable OAuthProvider provider, @RequestParam String redirectUri) {
        return CustomResponse.ofOk("인증 URL 생성 성공", oAuthProviderService.getOAuthLoginPageUrl(provider, redirectUri));
    }
    @GetMapping("/{provider}/local")
    public CustomResponse<String> getProviderAuthUrlForLocal(@PathVariable OAuthProvider provider, HttpServletRequest request) {
        return CustomResponse.ofOk("인증 URL 생성 성공", oAuthProviderService.getOAuthLoginPageUrlLocal(provider, request));
    }

    @PostMapping("/kakao")
    public ResponseEntity<CustomResponse<OAuthDtoInterface>> loginKakao(@RequestBody KakaoLoginParams params) {
        return loginOAuth(params);
    }

    @PostMapping("/naver")
    public ResponseEntity<CustomResponse<OAuthDtoInterface>> loginNaver(@RequestBody NaverLoginParams params) {
        return loginOAuth(params);
    }

    @PostMapping("/google")
    public ResponseEntity<CustomResponse<OAuthDtoInterface>> loginGoogle(@RequestBody GoogleLoginParams params) {
        return loginOAuth(params);
    }

    @PostMapping("/github")
    public ResponseEntity<CustomResponse<OAuthDtoInterface>> loginGithub(@RequestBody GithubLoginParams params) {
        return loginOAuth(params);
    }
    @PostMapping("/microsoft")
    public ResponseEntity<CustomResponse<OAuthDtoInterface>> loginMicrosoft(@RequestBody MicrosoftLoginParams params) {
        return loginOAuth(params);
    }
    @PostMapping("/twitter")
    public ResponseEntity<CustomResponse<OAuthDtoInterface>> loginTwitter(@RequestBody TwitterLoginParams params) {
        return loginOAuth(params);
    }
    @PostMapping("/facebook")
    public ResponseEntity<CustomResponse<OAuthDtoInterface>> loginFacebook(@RequestBody FacebookLoginParams params) {
        return loginOAuth(params);
    }

    private ResponseEntity<CustomResponse<OAuthDtoInterface>> loginOAuth(OAuthLoginParams params) {
        AuthResult result = oAuthLoginService.loginOrCreateTempAccount(params);
        CustomResponse<OAuthDtoInterface> response = CustomResponse
                .of(result.getHttpStatus(), result.getMessage(), result.getResponse());
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }


//    @PostMapping("/sign-up")
//    public ResponseEntity<CustomResponse<Void>> oAuthSignUp(@RequestBody @Valid OAuthSignUpDto oAuthSignUpDto) {
//        oAuthLoginService.signUp(oAuthSignUpDto);
//        CustomResponse<Void> signUpResponse = CustomResponse
//                .emptyData(HttpStatus.CREATED, "회원가입 완료");
//        return ResponseEntity
//                .status(signUpResponse.getHttpStatus())
//                .body(signUpResponse);
//    }

}
