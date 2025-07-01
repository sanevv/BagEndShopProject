package com.github.semiprojectshop.service.sihu.oauth;

import com.github.semiprojectshop.config.oauth.dto.userinfo.OAuthUserInfo;
import com.github.semiprojectshop.repository.sihu.social.SocialIdPk;
import com.github.semiprojectshop.repository.sihu.social.SocialIdsJpa;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.repository.sihu.user.MyUserJpa;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.OAuthLoginParams;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.AuthResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MyUserJpa myUserJpa;
    private final SocialIdsJpa socialIdsRepository;
    private final OAuthClientManager oAuthClientManager;
    private final OAuthCodeManager oAuthCodeManager;

    @Transactional
    public AuthResult loginOrCreateTempAccount(OAuthLoginParams params) {
        //소셜 서버에 요청해서 사용자 정보 받아오기
        OAuthUserInfo oAuthUserInfo = oAuthClientManager.request(params);
        SocialIdPk socialIdPk = new SocialIdPk(oAuthUserInfo.getSocialId(), oAuthUserInfo.getOAuthProvider());

        //소셜 아이디로 사용자 정보 조회
        //DB 에서 소셜 사용자 정보 찾기
        MyUser requestUser = myUserJpa.findBySocialIdPkOrUserEmail(socialIdPk, oAuthUserInfo.getEmail())
                .orElseGet(() -> processTempSignUp(oAuthUserInfo));//없으면 임시 회원가입 진행


        return null;
    }
    private MyUser processTempSignUp(OAuthUserInfo oAuthUserInfo){
        MyUser newUser = MyUser.fromOAuthUserInfo(oAuthUserInfo);
        return myUserJpa.save(newUser); //임시 회원가입처리
    }

}
