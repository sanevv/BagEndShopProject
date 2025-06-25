package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.repository.sihu.product.wish.QWish;
import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
                .orderBy(QProduct.product.createdAt.desc(),
                        QProduct.product.productId.asc()) //최신순 정렬 값이 같을경우 //productId로 오름차순 정렬
                .limit(10)
                .fetch();
    }

    @Override
    public PaginationDto<ProductListResponse> findCategoryProductList(ProductListRequest productListRequest, long loginUserId) {
        BooleanExpression categoryCondition = whereByCategory(productListRequest.getCategory());
        OrderSpecifier<? extends Comparable<?>> orderSpecifier = orderByCondition(productListRequest.getSort());

        Long totalCount = queryFactory.select(QProduct.product.count())
                .from(QProduct.product)
                .where(categoryCondition)
                .fetchOne();
        if (totalCount == null) return null;
        long totalPage = (totalCount + productListRequest.getSize() - 1) / productListRequest.getSize();//소수점은 절삭됨
        if(totalPage < productListRequest.getPage()) return null; //요청한 페이지가 총 페이지보다 크면 null 반환


        List<ProductListResponse> productListResponses = queryFactory.select(Projections.fields(ProductListResponse.class,
                        QProduct.product.productId,
                        QProduct.product.productName,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(QProductImage.productImage.imagePath)
                                        .from(QProductImage.productImage)
                                        .where(QProductImage.productImage.product.eq(QProduct.product)
                                                .and(QProductImage.productImage.thumbnail.isTrue()))
                                        .limit(1), "thumbnail"
                        ),
                        QProduct.product.price,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .selectOne()
                                        .from(QWish.wish)
                                        .where(QWish.wish.wishPk.myUser.userId.eq(loginUserId)
                                                .and(QWish.wish.wishPk.product.productId.eq(QProduct.product.productId)))
                                        .exists(), "wished"
                        )
                ))
                .from(QProduct.product)
                .where(categoryCondition)
                .orderBy(orderSpecifier, QProduct.product.productId.asc())
                .offset(productListRequest.getPage() * productListRequest.getSize())
                .limit(productListRequest.getSize())
                .fetch();


        return PaginationDto.of(
                productListRequest.getPage()+1, // 페이지는 1부터 시작하므로 +1
                productListRequest.getSize(),
                totalPage,
                totalCount,
                productListResponses
        );

    }

    private BooleanExpression whereByCategory(ProductListRequest.ProductCategoryRequest category) {
        if(category == ProductListRequest.ProductCategoryRequest.ALL) return null;

        Category.CategoryName categoryName =
                switch(category) {
                    case BACKPACK -> Category.CategoryName.BACK_PACK;
                    case CROSS -> Category.CategoryName.CROSS_BAG;
                    case MESSENGER -> Category.CategoryName.MESSENGER_BAG;
                    default -> throw new IllegalStateException("Unexpected value: " + category);
                };
        Category categoryEntity = Category.fromOnlyName(categoryName);

        return QProduct.product.category.eq(categoryEntity);
    }

    private OrderSpecifier<? extends Comparable<?>> orderByCondition(ProductListRequest.ProductSortRequest sort) {
        return switch (sort) {
            case PRICE_ASC -> QProduct.product.price.asc();
            case PRICE_DESC -> QProduct.product.price.desc();
            case NEWEST -> QProduct.product.createdAt.desc();
        };
    }
}
