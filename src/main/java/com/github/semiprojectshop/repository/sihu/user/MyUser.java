package com.github.semiprojectshop.repository.sihu.user;

import com.github.semiprojectshop.config.module.converter.MyUserStatusConverter;
import com.github.semiprojectshop.config.oauth.dto.userinfo.OAuthUserInfo;
import com.github.semiprojectshop.repository.sihu.social.SocialId;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@DynamicInsert
@Getter
@Table(name = "my_user")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String zipCode;
    private String address;
    private String addressDetails;
    private LocalDateTime registerAt;
    @Convert(converter = MyUserStatusConverter.class)
    @Column(length = 20)
    private MyUserStatus status;

    private String profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Roles roles;
    @OneToMany(mappedBy = "myUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialId> socialIds;


    public static MyUser onlyId(Long userId) {
        MyUser myUser = new MyUser();
        myUser.userId = userId;
        return myUser;
    }

    public static MyUser fromOAuthUserInfo(OAuthUserInfo oAuthUserInfo) {
        MyUser myUser = new MyUser();
        myUser.email = oAuthUserInfo.getEmail();
        myUser.name = oAuthUserInfo.getNickname();
        myUser.profileImage = oAuthUserInfo.getProfileImg() != null ?
                oAuthUserInfo.getProfileImg() :
                "https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/elderly-man-icon.png";

        //임시 값 넣어주기
        myUser.password = "tempPassword";

        myUser.phoneNumber = UUID.randomUUID().toString();
        myUser.zipCode = "000000";
        myUser.address = "임시 주소";
        myUser.roles = Roles.fromOnlyName(Roles.RoleName.ROLE_USER);


        return myUser;
    }
}
