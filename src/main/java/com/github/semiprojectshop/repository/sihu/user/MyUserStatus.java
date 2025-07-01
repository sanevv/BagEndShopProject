package com.github.semiprojectshop.repository.sihu.user;

import com.github.semiprojectshop.config.module.converter.MyEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MyUserStatus implements MyEnumInterface {
    NORMAL("정상"),
    LOCKED("잠김"),
    WITHDRAWN("탈퇴"),
    TEMPORARY("임시");
    private final String value;

}
