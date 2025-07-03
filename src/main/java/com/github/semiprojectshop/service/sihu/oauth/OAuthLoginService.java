package com.github.semiprojectshop.service.sihu.oauth;

import com.github.semiprojectshop.config.oauth.dto.userinfo.OAuthUserInfo;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sihu.social.SocialId;
import com.github.semiprojectshop.repository.sihu.social.SocialIdPk;
import com.github.semiprojectshop.repository.sihu.social.SocialIdsJpa;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.repository.sihu.user.MyUserJpa;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.OAuthLoginParams;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.AuthResult;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.OAuthSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
        SocialIdPk socialIdPk = SocialIdPk.of(oAuthUserInfo.getSocialId(), oAuthUserInfo.getOAuthProvider());

        //소셜 아이디로 사용자 정보 조회
        //DB 에서 소셜 사용자 정보 찾기
        MyUser requestUser = myUserJpa.findBySocialIdPkOrUserEmail(socialIdPk, oAuthUserInfo.getEmail())
                .orElseGet(() -> processTempSignUp(oAuthUserInfo));//없으면 임시 회원가입 진행

        requestUserSetSocialId(requestUser, socialIdPk);
        updateProfileImgFromOAuthInfo(oAuthUserInfo, requestUser);

        return requestUser.isEnabled() ?
                createOAuthLoginResponse(requestUser) :
                createOAuthSignUpResponse(oAuthUserInfo);
    }

    private AuthResult createOAuthSignUpResponse(OAuthUserInfo oAuthUserInfo) {
        OAuthSignUpDto oAuthSignUpDto = OAuthSignUpDto.fromOAuthUserInfo(oAuthUserInfo);
        return AuthResult.builder()
                .response(oAuthSignUpDto)
                .message("임시 회원가입 성공")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private AuthResult createOAuthLoginResponse(MyUser requestUser) {
        MemberVO memberVO = MemberVO.fromMyUserEntity(requestUser);
        return AuthResult.builder()
                .response(memberVO)
                .message("로그인 성공")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private void updateProfileImgFromOAuthInfo(OAuthUserInfo userInfo, MyUser requestUser) {
        if (userInfo.getProfileImg() != null && requestUser.getProfileImage() == null)
            requestUser.setProfileImage(userInfo.getProfileImg());

    }


    private MyUser processTempSignUp(OAuthUserInfo oAuthUserInfo) {
        MyUser newUser = MyUser.fromOAuthUserInfo(oAuthUserInfo);
        return myUserJpa.save(newUser); //임시 회원가입처리
    }

    private void requestUserSetSocialId(MyUser requestUser, SocialIdPk socialIdPk) {
        boolean hasSocialIdPk = requestUser.getSocialIds()!=null &&
                requestUser.getSocialIds().stream()
                .map(SocialId::getSocialIdPk)
                .anyMatch(pk -> pk.equals(socialIdPk));
        // 소셜 아이디가 이미 존재하는지 확인
        if (hasSocialIdPk) return;
        SocialId newSocialId = SocialId.ofSocialIdPkAndMyUser(socialIdPk, requestUser);
        requestUser.addSocialId(newSocialId);

    }

}
