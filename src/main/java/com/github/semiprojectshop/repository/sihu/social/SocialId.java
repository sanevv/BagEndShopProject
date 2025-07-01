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

    private LocalDateTime connectAt;

    public static SocialId ofSocialIdPkAndMyUser(SocialIdPk socialIdPk, MyUser myUser){
        SocialId socialId = new SocialId(socialIdPk.getSocialId(), socialIdPk.getSocialProvider(), myUser);
        socialId.connectAt = LocalDateTime.now();
        return socialId;
    }

    public SocialId(String socialId, OAuthProvider provider, MyUser myUser) {
        this.socialIdPk = new SocialIdPk(socialId, provider);
        this.myUser = myUser;
    }
    public void socialConnectSetting(MyUser myUser){
        this.connectAt = LocalDateTime.now();
        this.myUser = myUser;
    }
    public void socialConnectSetting(){
        this.connectAt = LocalDateTime.now();
    }

}
