package com.github.semiprojectshop.repository.sihu.user;

import com.github.semiprojectshop.repository.sihu.social.QSocialId;
import com.github.semiprojectshop.repository.sihu.social.SocialIdPk;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MyUserJpaCustomImpl implements MyUserJpaCustom {
    private final JPAQueryFactory queryFactory;
    private final QMyUser qMyUser = QMyUser.myUser;

    @Override
    public Optional<MyUser> findBySocialIdPkOrUserEmail(SocialIdPk socialIdPk, String email) {
        QSocialId qSocialId = QSocialId.socialId;
        List<MyUser> myUserList = queryFactory.select(qMyUser)
                .from(qMyUser)
                .leftJoin(qMyUser.socialIds, qSocialId).fetchJoin()
                .leftJoin(qMyUser.roles, QRoles.roles).fetchJoin()
                .where(qSocialId.socialIdPk.eq(socialIdPk).or(qMyUser.email.eq(email)))
                .fetch();
        MyUser response = singleOutAUser(myUserList, socialIdPk);
        return Optional.ofNullable(response);
    }

    private MyUser singleOutAUser(List<MyUser> myUserList, SocialIdPk socialIdPk) {
        return myUserList.isEmpty() ? null :
                myUserList.size() == 1 ? myUserList.get(0) :
                        myUserList.stream().filter(user ->
                                        user.getSocialIds().stream().anyMatch(id -> id.getSocialIdPk().equals(socialIdPk)))
                                .findFirst().orElse(null);
    }
}
