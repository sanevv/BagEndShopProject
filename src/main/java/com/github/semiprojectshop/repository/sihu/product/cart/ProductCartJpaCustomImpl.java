package com.github.semiprojectshop.repository.sihu.product.cart;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
}
