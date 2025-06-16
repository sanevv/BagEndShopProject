package com.github.mymvcspring.repository.user;

import com.github.mymvcspring.web.dto.auth.UserInfoEditRequest;
import com.github.mymvcspring.web.dto.member.MemberListResponse;
import com.github.mymvcspring.web.dto.member.SearchConditions;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public long updateUserInfo(UserInfoEditRequest editRequest) {
        return queryFactory.update(QMyUser.myUser)
                .set(QMyUser.myUser.userName, editRequest.getUserName())
                .set(QMyUser.myUser.email, editRequest.getEmail())
                .set(QMyUser.myUser.phoneNumber, editRequest.getPhoneNumberValue())
                .set(QMyUser.myUser.password, editRequest.getPassword())
                .set(QMyUser.myUser.zipCode, editRequest.getZipCode())
                .set(QMyUser.myUser.address, editRequest.getAddress())
                .set(QMyUser.myUser.addressDetail, editRequest.getAddressDetail())
                .set(QMyUser.myUser.addressReference, editRequest.getAddressReference())
                .where(QMyUser.myUser.userId.eq(editRequest.getUserId()))
                .execute(); // userId가 없으면 0이 반환된다.
    }

    @Override
    public List<MemberListResponse> findUserInfoBySearchConditions(SearchConditions searchConditions, long page, long size) {

        BooleanExpression predicate = searchConditions.getSearchValue() != null && !searchConditions.getSearchValue().isBlank() ?
                createSearchPredicate(searchConditions)
                : null;
        return queryFactory.select(Projections.fields(MemberListResponse.class,
                QMyUser.myUser.userId,
                QMyUser.myUser.userName,
                QMyUser.myUser.email.as("encryptedEmail"),
                QMyUser.myUser.gender
                        )
                )
                .from(QMyUser.myUser)
                .where(
                        predicate != null
                                ? predicate.and(QMyUser.myUser.userId.ne("admin"))
                                : QMyUser.myUser.userId.ne("admin")) // 관리자 제외
                .orderBy(QMyUser.myUser.registerAt.desc())
                .offset(page * size)
                .limit(size)
                .fetch();
    }

    private BooleanExpression createSearchPredicate(SearchConditions searchConditions) {
        return switch (searchConditions.getSearchType()){
            case USER_ID -> QMyUser.myUser.userId.containsIgnoreCase(searchConditions.getSearchValue());
            case USER_NAME -> QMyUser.myUser.userName.containsIgnoreCase(searchConditions.getSearchValue());
            case EMAIL -> QMyUser.myUser.email.containsIgnoreCase(searchConditions.getSearchValue());
        };

    }
}
