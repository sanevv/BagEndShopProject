package com.github.mymvcspring.repository.user;

public interface MyUserRepositoryCustom {

    MyUser findByIdAndJoinLastLogin(String userId);

    String findUserIdByEmailAndName(String email, String name);

    long updatePasswordByUserId(String userId, String encrypt);
}
