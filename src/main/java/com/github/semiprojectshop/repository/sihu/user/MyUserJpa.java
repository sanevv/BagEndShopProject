package com.github.semiprojectshop.repository.sihu.user;

import com.github.semiprojectshop.repository.sihu.social.SocialIdPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserJpa extends JpaRepository<MyUser, Long>, MyUserJpaCustom {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    MyUser findByEmail(String email);

    boolean existsByPassword(String password);

    Optional<MyUser> findBySocialIdsSocialIdPk(SocialIdPk socialIdPk);

}
