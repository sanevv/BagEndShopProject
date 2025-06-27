package com.github.semiprojectshop.repository.sihu.product.cart;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.product.QProductImage;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartRequest;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductCartJpaCustomImpl implements ProductCartJpaCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public long addQuantity(AddToCartRequest addToCartRequest, long loginUserId) {

        return queryFactory.update(QProductCart.productCart)
                .set(QProductCart.productCart.quantity, QProductCart.productCart.quantity.add(addToCartRequest.getQuantity()))
                .where(
                        QProductCart.productCart.product.productId.eq(addToCartRequest.getProductId())
                                .and(QProductCart.productCart.myUser.userId.eq(loginUserId))
                )
                .execute();
    }

    @Override
    public List<CartListResponse> findAllByUserId(long loginUserId) {
        return queryFactory.select(
                        Projections.fields(CartListResponse.class,
                                QProductCart.productCart.productCartId,
                                QProductCart.productCart.product.productId,
                                QProductCart.productCart.product.productName,
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(QProductImage.productImage.imagePath)
                                                .from(QProductImage.productImage)
                                                .where(QProductImage.productImage.product.productId.eq(QProductCart.productCart.product.productId)
                                                        .and(QProductImage.productImage.thumbnail.isTrue())).limit(1), "productImage"
                                ),
                                QProductCart.productCart.quantity,
                                QProductCart.productCart.product.price
                        ))
                .from(QProductCart.productCart)
                .where(QProductCart.productCart.myUser.userId.eq(loginUserId))
                .orderBy(QProductCart.productCart.createdAt.desc())
                .fetch();
    }

    @Override
    public long updateProductQuantity(long productCartId, int quantity) {
        return queryFactory.update(QProductCart.productCart)
                .set(QProductCart.productCart.quantity, quantity)
                .where(QProductCart.productCart.productCartId.eq(productCartId))
                .execute();
    }
}
