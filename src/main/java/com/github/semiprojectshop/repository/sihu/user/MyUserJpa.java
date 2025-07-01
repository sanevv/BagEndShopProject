package com.github.semiprojectshop.repository.sihu.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserJpa extends JpaRepository<MyUser, Long>, MyUserJpaCustom {
}
