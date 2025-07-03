package com.github.semiprojectshop.repository.sihu.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyUserJpa extends JpaRepository<MyUser, Long>, MyUserJpaCustom {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM MyUser u JOIN FETCH u.roles where u.email = :email")
    MyUser findByEmailFetchJoin(String email);

    boolean existsByPasswordAndEmail(String password, String email);
}
