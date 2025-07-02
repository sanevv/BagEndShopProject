package com.github.semiprojectshop.repository.sihu.social;

import com.github.semiprojectshop.repository.sihu.user.MyUser;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_id")
@DynamicInsert
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "socialIdPk")
public class SocialId {

   @EmbeddedId
   private SocialIdPk socialIdPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser myUser;

    private LocalDateTime connectedAt;

    public static SocialId ofSocialIdPkAndMyUser(SocialIdPk socialIdPk, MyUser myUser){
        SocialId socialId = new SocialId(socialIdPk.getSocialId(), socialIdPk.getSocialProvider(), myUser);
        socialId.connectedAt = LocalDateTime.now();
        return socialId;
    }

    private SocialId(String socialId, OAuthProvider provider, MyUser myUser) {
        this.socialIdPk = SocialIdPk.of(socialId, provider);
        this.myUser = myUser;
    }
    public void socialConnectSetting(MyUser myUser){
        this.connectedAt = LocalDateTime.now();
        this.myUser = myUser;
    }
    public void socialConnectSetting(){
        this.connectedAt = LocalDateTime.now();
    }

}
