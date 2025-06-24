package com.github.semiprojectshop.web.kyeongsoo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindEmailResponse {
    private String userName;
    private String phoneNumber;
    private boolean existUser;
}
