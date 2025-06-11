package com.github.mymvcspring.repository.user;

public interface LoginHistoryRepositoryCustom {
    LoginHistory findByLastLogin(String userId);
}
