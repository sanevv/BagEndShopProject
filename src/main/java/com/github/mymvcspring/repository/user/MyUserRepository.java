package com.github.mymvcspring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyUserRepository extends JpaRepository<MyUser, String> {
    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM MyUser u WHERE u.userId = :userId")
    boolean existsByIdCustom(String userId);

    @Query("SELECT u FROM MyUser u WHERE u.email = :email")
    MyUser findByCustom(String email);

}
