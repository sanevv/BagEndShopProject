package com.github.semiprojectshop.repository.aery.user.model;

import java.sql.SQLException;
import java.util.List;

import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;

public interface WishDAO {
	
	// 로그인한 사용자가 본인의 위시리스트를 조회
	List<ProductDetailVO> selectWishListByUserId(int userId) throws SQLException;

	// 사용자가 해당 상품을 찜했는지 확인
	boolean exists(int userId, int productId) throws SQLException;
	
	// 위시리스트에 상품 추가
	void insert(int userId, int productId) throws SQLException;
	
	//위시리스트에서 상품 제거
	void delete(int userId, int productId) throws SQLException;
	
	// 위시리스트 상태를 토글 (있으면 삭제, 없으면 추가)
	void toggle(int userId, int productId) throws SQLException;
}
