package com.github.semiprojectshop.repository.kyeongsoo.memberDomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class MemberVO {

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


}
