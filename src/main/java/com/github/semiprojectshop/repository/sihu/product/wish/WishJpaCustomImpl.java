package com.github.semiprojectshop.repository.sihu.product.wish;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WishJpaCustomImpl implements WishJpaCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public long steamingProductByUserId(long productId, long loginUserId) {
        WishPk wishPk = WishPk.of(Product.onlyId(productId), MyUser.onlyId(loginUserId));
        Long count = queryFactory.select(QWish.wish.wishPk.count())
                .from(QWish.wish)
                .where(QWish.wish.wishPk.eq(wishPk))
                .fetchOne();
        if (count != null && count > 0) {
            return queryFactory.delete(QWish.wish)
                    .where(QWish.wish.wishPk.eq(wishPk))
                    .execute();
        }
        return queryFactory.insert(QWish.wish)
                .columns(QWish.wish.wishPk.product.productId,
                         QWish.wish.wishPk.myUser.userId)
                .values(productId, loginUserId)
                .execute();
    }
}
