package com.github.semiprojectshop.service.sihu.oauth;

import com.github.semiprojectshop.config.oauth.dto.userinfo.OAuthUserInfo;
import com.github.semiprojectshop.config.oauth.dto.userinfo.TwitterUserInfo;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sihu.social.SocialId;
import com.github.semiprojectshop.repository.sihu.social.SocialIdPk;
import com.github.semiprojectshop.repository.sihu.social.SocialIdsJpa;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.repository.sihu.user.MyUserJpa;
import com.github.semiprojectshop.service.sihu.StorageService;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.OAuthLoginParams;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.AuthResult;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.OAuthSignUpDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MyUserJpa myUserJpa;
    private final SocialIdsJpa socialIdsRepository;
    private final OAuthClientManager oAuthClientManager;
    private final OAuthCodeManager oAuthCodeManager;
    private final StorageService storageService;

    @Transactional
    public AuthResult loginOrCreateTempAccount(OAuthLoginParams params) {
        //소셜 서버에 요청해서 사용자 정보 받아오기
        OAuthUserInfo oAuthUserInfo = oAuthClientManager.request(params);
        SocialIdPk socialIdPk = SocialIdPk.of(oAuthUserInfo.getSocialId(), oAuthUserInfo.getOAuthProvider());

        //소셜 아이디로 사용자 정보 조회
        //DB 에서 소셜 사용자 정보 찾기
        MyUser requestUser = myUserJpa.findBySocialIdPkOrUserEmail(socialIdPk, oAuthUserInfo.getEmail())
                .orElseGet(() -> processTempSignUp(oAuthUserInfo));//없으면 임시 회원가입 진행

        boolean isConnection = requestUserSetSocialId(requestUser, socialIdPk);
        updateProfileImgFromOAuthInfo(oAuthUserInfo, requestUser);



        return requestUser.isEnabled() ?
                createOAuthLoginResponse(requestUser, isConnection) :
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

    private AuthResult createOAuthLoginResponse(MyUser requestUser, boolean isConnection) {
        MemberVO memberVO = MemberVO.fromMyUserEntity(requestUser);
        return AuthResult.builder()
                .isConnection(isConnection)
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

    private boolean requestUserSetSocialId(MyUser requestUser, SocialIdPk socialIdPk) {
        boolean hasSocialIdPk = requestUser.getSocialIds()!=null &&
                requestUser.getSocialIds().stream()
                .map(SocialId::getSocialIdPk)
                .anyMatch(pk -> pk.equals(socialIdPk));
        // 소셜 아이디가 이미 존재하는지 확인
        if (hasSocialIdPk) return false;// 이미 존재하는 소셜 아이디라면 추가하지 않음
        SocialId newSocialId = SocialId.ofSocialIdPkAndMyUser(socialIdPk, requestUser);
        requestUser.addSocialId(newSocialId);
        return true;//추가했음을 알림
    }
    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phone) {
        String phoneOnlyNumber = phone.replaceAll("[^\\d]", "");
        return myUserJpa.existsByPhoneNumber(phoneOnlyNumber);
    }
    @Transactional
    public void signUp(@Valid OAuthSignUpDto oAuthSignUpDto) {
        //소셜 아이디로 사용자 정보 조회
        SocialIdPk socialIdPk = SocialIdPk.of(oAuthSignUpDto.getSocialId(), oAuthSignUpDto.getProvider());
        MyUser requestUser = myUserJpa.findBySocialIdsSocialIdPk(socialIdPk)
                .orElseThrow(() -> CustomMyException.fromMessage("소셜 아이디로 임시 가입된 사용자를 찾을 수 없습니다."));
        if(requestUser.isEnabled())
            throw CustomMyException.fromMessage("이미 가입이 완료된 사용자입니다.");

        if(!(oAuthSignUpDto.getProfileImageFile() == null || oAuthSignUpDto.getProfileImageFile().isEmpty())){
            String profileImageUrl = transferProfileImageAfterUrl(oAuthSignUpDto.getProfileImageFile(), requestUser.getUserId());
            oAuthSignUpDto.setProfileImageUrl(profileImageUrl);
        }
        requestUser.modifyOauthSignUp(oAuthSignUpDto);
    }
    private String transferProfileImageAfterUrl(MultipartFile file, Long userId){
        Path path = storageService.createFileDirectory("user", userId.toString(), "profile");
        return storageService.returnTheFilePathAfterTransfer(file, path);


    }
}

