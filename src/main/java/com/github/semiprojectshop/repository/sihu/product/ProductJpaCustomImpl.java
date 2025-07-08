package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.repository.sihu.product.wish.QWish;
import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.OrderProductRequest;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class ProductJpaCustomImpl implements ProductJpaCustom {
    private final JPAQueryFactory queryFactory;

 // DataSource 자동생성
 	private final DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
 	private Connection conn;
 	private PreparedStatement pstmt;
 	private ResultSet rs;

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
    public PaginationDto<ProductListResponse> findCategoryProductList(ProductListRequest productListRequest, Long loginUserId) {
        BooleanExpression categoryCondition = whereByCategory(productListRequest.getCategory());

        //소재 + 상품명 + 상품소개로 검색조건 설정
        BooleanExpression searchCondition = whereBySearchKeyword(productListRequest.getSearchKeyword());
        OrderSpecifier<? extends Comparable<?>> orderSpecifier = orderByCondition(productListRequest.getSort());

        Long totalCount = queryFactory.select(QProduct.product.count())
                .from(QProduct.product)
                .where(categoryCondition)
                .fetchOne();
        if (totalCount == null) return null;
        long totalPage = (totalCount + productListRequest.getSize() - 1) / productListRequest.getSize();//소수점은 절삭됨
        if(totalPage < productListRequest.getPage()) return null; //요청한 페이지가 총 페이지보다 크면 null 반환

        BooleanExpression wishedCondition = (loginUserId != null)
                ? QWish.wish.wishPk.myUser.userId.eq(loginUserId)
                .and(QWish.wish.wishPk.product.productId.eq(QProduct.product.productId))
                : Expressions.FALSE;


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
                                        .where(wishedCondition)
                                        .exists(), "wished"
                        )
                ))
                .from(QProduct.product)
                .where(categoryCondition,searchCondition)
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

    private BooleanExpression whereBySearchKeyword(String searchKeyword) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return null;
        }
        return QProduct.product.productName.containsIgnoreCase(searchKeyword)
                .or(QProduct.product.productInfo.containsIgnoreCase(searchKeyword))
                .or(QProduct.product.matter.containsIgnoreCase(searchKeyword));
    }

    private void settingQuantityForOrder(List<CartListResponse> cartListResponses, List<OrderProductRequest> orderProductRequests) {
        for (CartListResponse cartListResponse : cartListResponses) {
            for (OrderProductRequest orderProductRequest : orderProductRequests) {
                if (cartListResponse.getProductId() == orderProductRequest.getProductId()) {
                    cartListResponse.settingForOrder(orderProductRequest.getCartId(), orderProductRequest.getQuantity());
                }
            }
        }
    }

    @Override
    public List<CartListResponse> findProductInfoForOrder(List<OrderProductRequest> orderProductRequests) {
        List<CartListResponse> cartListResponses = queryFactory.select(Projections.fields(CartListResponse.class,
                        QProduct.product.productId,
                        QProduct.product.productName,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(QProductImage.productImage.imagePath)
                                        .from(QProductImage.productImage)
                                        .where(QProductImage.productImage.product.eq(QProduct.product)
                                                .and(QProductImage.productImage.thumbnail.isTrue()))
                                        .limit(1), "productImage"
                        ),
                        QProduct.product.price
                ))
                .from(QProduct.product)
                .where(QProduct.product.productId.in(orderProductRequests.stream().map(OrderProductRequest::getProductId).toList()))
                .fetch();
        settingQuantityForOrder(cartListResponses,orderProductRequests);
        return cartListResponses;
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

    
    // 사용한 자원을 반납하는 close() 메소드 생성하기
 	private void close() {
 		try {
 			if(rs    != null) {rs.close();	  rs=null;}
 			if(pstmt != null) {pstmt.close(); pstmt=null;}
 			if(conn  != null) {conn.close();  conn=null;}
 		} catch(SQLException e) {
 			e.printStackTrace();
 		}
 	}// end of private void close()---------------
    
 	
    // tbl_map(위,경도) 테이블에 있는 정보를 가져오기(select)
	@Override
	public List<Map<String, String>> selectStoreMap() throws SQLException {
		List<Map<String, String>> storeMapList = new ArrayList<>();
	      
		try {
			conn = ds.getConnection();
	         
			String sql = " select storeID, storeName, storeUrl, storeImg, storeAddress, lat, lng, zindex " + 
						 " from tbl_map " + 
	                     " order by zindex asc ";
	         
			pstmt = conn.prepareStatement(sql);
	         
			rs = pstmt.executeQuery();
	         
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
	            map.put("STOREID", rs.getString("STOREID"));
	            map.put("STORENAME", rs.getString("STORENAME"));
	            map.put("STOREURL", rs.getString("STOREURL"));
	            map.put("STOREIMG", rs.getString("STOREIMG"));
	            map.put("STOREADDRESS", rs.getString("STOREADDRESS"));
	            map.put("LAT", rs.getString("LAT"));
	            map.put("LNG", rs.getString("LNG"));
	            map.put("ZINDEX", rs.getString("ZINDEX"));
	                        
	            storeMapList.add(map); 
			}// end of while-----------------
	         
		} finally {
			close();
		}
	      
		return storeMapList;
	}
}
