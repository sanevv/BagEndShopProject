package com.github.semiprojectshop.repository.kyeongsoo.memberDomain;

import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.OAuthDtoInterface;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class MemberVO implements OAuthDtoInterface {

    private int userId;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String zipCode; // zipCode String
    private String address;
    private String addressDetails;
    private String registerAt;
    private int roleId = 2; // roleId 기본값 2(일반사용자) - 애리

    public static MemberVO fromMyUserEntity(MyUser requestUser) {
        MemberVO memberVO = new MemberVO();
        memberVO.userId = Math.toIntExact(requestUser.getUserId());
        memberVO.email = requestUser.getEmail();
        memberVO.password = requestUser.getPassword();
        memberVO.name = requestUser.getName();
        memberVO.phoneNumber = requestUser.getPhoneNumber();
        memberVO.zipCode = requestUser.getZipCode();
        memberVO.address = requestUser.getAddress();
        memberVO.addressDetails = requestUser.getAddressDetails();
        memberVO.registerAt = requestUser.getRegisterAt().toString();
        memberVO.roleId = requestUser.getRoles() != null ?
                Math.toIntExact(requestUser.getRoles().getRoleId())
                : 2; // 기본값 2
        return memberVO;
    }
}
