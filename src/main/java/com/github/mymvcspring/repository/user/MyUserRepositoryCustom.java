package com.github.mymvcspring.repository.user;

import com.github.mymvcspring.web.dto.auth.UserInfoEditRequest;
import com.github.mymvcspring.web.dto.member.MemberListResponse;
import com.github.mymvcspring.web.dto.member.SearchConditions;

import java.util.List;

public interface MyUserRepositoryCustom {

    MyUser findByIdAndJoinLastLogin(String userId);

    String findUserIdByEmailAndName(String email, String name);

    long updatePasswordByUserId(String userId, String encrypt);

    long updateUserInfo(UserInfoEditRequest editRequest);

    List<MemberListResponse> findUserInfoBySearchConditions(SearchConditions searchConditions, long page, long size);
}
