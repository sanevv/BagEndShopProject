package com.github.semiprojectshop.web.kyeongsoo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordByEmailResponse {

    private String email;
    private String username;
    private String userid;
    private boolean isExistEmail; // 이메일로 비밀번호 찾기 여부

}
