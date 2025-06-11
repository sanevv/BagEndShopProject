package com.github.mymvcspring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long>, LoginHistoryRepositoryCustom {

}
