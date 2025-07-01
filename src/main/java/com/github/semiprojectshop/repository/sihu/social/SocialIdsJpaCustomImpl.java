package com.github.semiprojectshop.repository.sihu.social;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SocialIdsJpaCustomImpl implements SocialIdsJpaCustom {
    private final JPAQueryFactory queryFactory;

//
//    @Override
//    public Optional<SocialId> findBySocialIdPkJoinMyUser(SocialIdPk socialIdPk) {
//        QSocialId qSocialId = QSocialId.socialId;
//
//        SocialId socialId = queryFactory
//                .selectFrom(qSocialId)
//                .join(qSocialId.myUser, QMyUser.myUser).fetchJoin()
//                .where(qSocialId.socialIdPk.eq(socialIdPk))
//                .fetchOne();
//
//        return socialId == null ? Optional.empty() : Optional.of(socialId);
//    }
}
