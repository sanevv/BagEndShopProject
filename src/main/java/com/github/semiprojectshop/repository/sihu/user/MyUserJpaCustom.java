package com.github.semiprojectshop.repository.sihu.user;

import com.github.semiprojectshop.repository.sihu.social.SocialIdPk;

import java.util.Optional;

public interface MyUserJpaCustom {
    Optional<MyUser> findBySocialIdPkOrUserEmail(SocialIdPk socialIdPk, String email);
}
