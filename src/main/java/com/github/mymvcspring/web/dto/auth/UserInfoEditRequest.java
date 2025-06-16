package com.github.mymvcspring.web.dto.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoEditRequest {
    private String userId;
    private String userName;
    private String email;
    private String password;
    @Getter(AccessLevel.NONE)
    private String hp1;
    @Getter(AccessLevel.NONE)
    private String hp2;
    @Getter(AccessLevel.NONE)
    private String hp3;

    private String phoneNumberValue;
    public String getPhoneNumber() {
        if( hp1 == null || hp2 == null || hp3 == null) {
            return null;
        }
        return hp1 + "-" + hp2 + "-" + hp3;
    }
    private String address;
    private String addressDetail;
    private String addressReference;
    private String zipCode;

}
