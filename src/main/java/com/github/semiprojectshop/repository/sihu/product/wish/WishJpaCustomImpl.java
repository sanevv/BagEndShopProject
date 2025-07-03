package com.github.semiprojectshop.repository.sihu.product.wish;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.product.QProductImage;
import com.github.semiprojectshop.repository.sihu.product.cart.QProductCart;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.web.sihu.dto.product.wish.WishResponse;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    @Override
    public List<WishResponse> findMyWishListByUserId(long userId) {


        return queryFactory
                .select(Projections.fields(
                        WishResponse.class,
                        QWish.wish.wishPk.product.productId,
                        QWish.wish.wishPk.product.productName,
                        QWish.wish.wishPk.product.price.as("productPrice"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(QProductImage.productImage.imagePath)
                                        .from(QProductImage.productImage)
                                        .where(QProductImage.productImage.product.productId.eq(QWish.wish.wishPk.product.productId)
                                                .and(QProductImage.productImage.thumbnail.isTrue())).limit(1), "productThumbnailUrl"
                        )
                ))
                .from(QWish.wish)
                .where(QWish.wish.wishPk.myUser.userId.eq(userId))
                .orderBy(QWish.wish.createdAt.desc())
                .fetch();
    }

    @Override
    public List<WishResponse> findWishListPaging(long userId, int pageNo, int sizePerPage) {
        int offset = (pageNo - 1) * sizePerPage;

        return queryFactory
            .select(Projections.fields(
                WishResponse.class,
                QWish.wish.wishPk.product.productId,
                QWish.wish.wishPk.product.productName,
                QWish.wish.wishPk.product.price.as("productPrice"),
                ExpressionUtils.as(
                    JPAExpressions
                        .select(QProductImage.productImage.imagePath)
                        .from(QProductImage.productImage)
                        .where(
                            QProductImage.productImage.product.productId
                                .eq(QWish.wish.wishPk.product.productId)
                                .and(QProductImage.productImage.thumbnail.isTrue())
                        )
                        .limit(1),
                    "productThumbnailUrl"
                )
            ))
            .from(QWish.wish)
            .where(QWish.wish.wishPk.myUser.userId.eq(userId))
            .orderBy(QWish.wish.createdAt.desc())
            .offset(offset)
            .limit(sizePerPage)
            .fetch();
    }

    @Override
    public int countWishByUserId(long userId) {
        return queryFactory
            .select(QWish.wish.count())
            .from(QWish.wish)
            .where(QWish.wish.wishPk.myUser.userId.eq(userId))
            .fetchOne()
            .intValue();
    }
}
