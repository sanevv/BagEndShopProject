package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductJpaCustomImpl implements ProductJpaCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<MainProductResponse> findMainProductList() {



        return queryFactory.select(Projections.fields(MainProductResponse.class,
                QProduct.product.productId,
                QProduct.product.productName,
                ExpressionUtils.as(//서브쿼리 썸네일 가져오기
                        JPAExpressions
                                .select(QProductImage.productImage.imagePath)
                                .from(QProductImage.productImage)
                                .where(QProductImage.productImage.product.eq(QProduct.product)
                                        .and(QProductImage.productImage.thumbnail.isTrue()))
                                .limit(1), "thumbnail"
                )
                ))
                .from(QProduct.product)
                .orderBy(QProduct.product.createdAt.desc())
                .limit(10)
                .fetch();
    }
}
