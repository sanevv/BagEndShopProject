package com.github.semiprojectshop.repository.aery.user.model;

import java.sql.SQLException;
import java.util.List;
import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;

public interface WishDAO {
	
	// 로그인한 사용자가 본인의 관심상품을 조회
	List<ProductDetailVO> selectWishListByUser(String email) throws SQLException;

	// 사용자가 해당 상품을 관심상품에 등록했는지 확인
	boolean exists(int userId, int productId) throws SQLException;
	
	// 관다음품 등록
	void insert(int userId, int productId) throws SQLException;
	
	// 관심상품 제거
	void delete(int userId, int productId) throws SQLException;
	
	// 관심상품 등록 상태를 토글 (있으면 삭제, 없으면 추가)
	void toggle(int userId, int productId) throws SQLException;
	
	// 관심상품을 장바구니에 담기
	void wishToCart(int userId, int productId) throws SQLException;

	// 관심상품을 기반으로 주문 생성 (orders + orders_product에 insert), orders_id 생성 반환
	int createWishOrder(int userId, int productId) throws SQLException;
}
