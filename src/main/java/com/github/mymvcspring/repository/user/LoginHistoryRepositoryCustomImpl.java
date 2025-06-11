package com.github.mymvcspring.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginHistoryRepositoryCustomImpl implements LoginHistoryRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    @Override
    public LoginHistory findByLastLogin(String userId) {
        return queryFactory.selectFrom(QLoginHistory.loginHistory)
                .where(QLoginHistory.loginHistory.user.userId.eq(userId))
                .orderBy(QLoginHistory.loginHistory.loginDate.desc())
                .fetchFirst();
    }
}
