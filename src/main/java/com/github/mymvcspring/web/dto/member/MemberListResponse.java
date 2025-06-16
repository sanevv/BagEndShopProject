package com.github.mymvcspring.web.dto.member;

import com.github.mymvcspring.config.BeanProperties;
import lombok.*;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MemberListResponse {
    private String userId;
    private String userName;
    @Getter(AccessLevel.NONE)
    private String encryptedEmail;
    private String email;
    private String gender;
    private Integer searchCount;

    public void emailDecryption() {
        if(this.encryptedEmail == null)  return;
        this.email = BeanProperties.decryptNotException(this.encryptedEmail);

    }
}
