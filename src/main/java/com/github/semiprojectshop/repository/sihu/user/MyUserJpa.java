package com.github.semiprojectshop.repository.sihu.user;

import com.github.semiprojectshop.repository.sihu.social.SocialIdPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MyUserJpa extends JpaRepository<MyUser, Long>, MyUserJpaCustom {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    MyUser findByEmail(String email);

    @Query("SELECT u FROM MyUser u JOIN FETCH u.roles where u.email = :email")
    MyUser findByEmailFetchJoin(String email);

    boolean existsByPasswordAndEmail(String password, String email);
    boolean existsByPassword(String password);

    Optional<MyUser> findBySocialIdsSocialIdPk(SocialIdPk socialIdPk);

}
