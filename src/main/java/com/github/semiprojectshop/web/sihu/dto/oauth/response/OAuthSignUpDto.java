package com.github.semiprojectshop.web.sihu.dto.oauth.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.semiprojectshop.config.oauth.dto.userinfo.OAuthUserInfo;
import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class OAuthSignUpDto implements OAuthDtoInterface{
    @NotBlank(message = "소셜 식별자 값은 필수입니다.")
    @Schema(description = "소셜 아이디", example = "Z9Vp6uyQ1S03CtxKHCnFS80KItHrRxIuwWse12EIupw")
    private String socialId;
    @NotNull(message = "소셜 공급자 값은 필수입니다.")
    private OAuthProvider provider;

    private String email;
    private String name;
//    @JsonProperty(access =  JsonProperty.Access.READ_ONLY)
    private String profileImageUrl;

    //역직렬화 전용 필드
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String zipCode;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String address;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String addressDetails;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile profileImageFile;


    public static OAuthSignUpDto fromOAuthUserInfo (OAuthUserInfo oAuthUserInfo){
        OAuthSignUpDto oAuthSignUpDto = new OAuthSignUpDto();
        oAuthSignUpDto.socialId = oAuthUserInfo.getSocialId();
        oAuthSignUpDto.provider = oAuthUserInfo.getOAuthProvider();
        oAuthSignUpDto.email = oAuthUserInfo.getEmail();
        oAuthSignUpDto.name = oAuthUserInfo.getNickname();
        oAuthSignUpDto.profileImageUrl = oAuthUserInfo.getProfileImg();
        return oAuthSignUpDto;
    }
    public void phoneNumberReplace(){
        //숫자만 남기고 모두제거
        this.phoneNumber = this.phoneNumber.replaceAll("[^0-9]", "");
    }
}
