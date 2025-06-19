package com.github.semiprojectshop.web.sihu.dto.member;

import com.github.semiprojectshop.config.BeanProperties;
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

    @Setter
    private long searchCount;

    public void emailDecryption() {
        if(this.encryptedEmail == null)  return;
        this.email = BeanProperties.decryptNotException(this.encryptedEmail);

    }
}
