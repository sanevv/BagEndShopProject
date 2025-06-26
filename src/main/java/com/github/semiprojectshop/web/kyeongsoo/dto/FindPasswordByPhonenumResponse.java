package com.github.semiprojectshop.web.kyeongsoo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordByPhonenumResponse {
    private String phonenum;
    private String username;
    private String userid;
    private boolean existPhonenum; // 휴대폰 번호로 비밀번호 찾기 여부

}
