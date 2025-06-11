package com.github.mymvcspring.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MyUserRepositoryCustomImpl implements MyUserRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public MyUser findByIdAndJoinLastLogin(String userId) {
        return queryFactory.selectFrom(QMyUser.myUser)
                .leftJoin(QMyUser.myUser.loginHistories, QLoginHistory.loginHistory).fetchJoin()
                .where(QMyUser.myUser.userId.eq(userId))
                .orderBy(QLoginHistory.loginHistory.loginDate.desc())
                .fetchFirst();       // 히스토리가 여러개 전부 다들어온다.
    }

    @Override
    public String findUserIdByEmailAndName(String email, String name) {
        return queryFactory.select(QMyUser.myUser.userId)
                .from(QMyUser.myUser)
                .where(QMyUser.myUser.email.eq(email)
                        .and(QMyUser.myUser.userName.eq(name)))
                .fetchFirst(); // userId가 없으면 null이 반환된다.
    }


    @Override
    public long updatePasswordByUserId(String userId, String encrypt) {
        return queryFactory.update(QMyUser.myUser)
                .set(QMyUser.myUser.password, encrypt)
                .set(QMyUser.myUser.lastChangeAt, LocalDateTime.now())
                .where(QMyUser.myUser.userId.eq(userId))
                .execute(); // userId가 없으면 0이 반환된다.
        // 주의: update는 반환값이 long이다. 영향받은 row의 개수를 반환한다.
    }
}
