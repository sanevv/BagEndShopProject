package com.github.semiprojectshop.repository.aery.wish.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.github.semiprojectshop.repository.kyeongsoo.productDomain.ProductVO;

public interface WishDAO {
	
	// 관심상품 목록 조회 (email 기준)
//	List<ProductVO> selectWishListByUser(String email) throws SQLException; 

	// 사용자가 해당 상품을 관심상품에 등록했는지 확인
	boolean exists(int userId, int productId) throws SQLException;
	
	// 관심상품 등록
	void insert(int userId, int productId) throws SQLException;
	
	// 관심상품 이미지 우측 x 버튼을 통한 관심상품 단일 삭제
	void deleteOne(int userId, int productId) throws SQLException;
	
	// 관심상품 등록 상태를 토글 (있으면 삭제, 없으면 추가)
	void toggle(int userId, int productId) throws SQLException;
	
	// 관심상품을 장바구니에 담기
	void wishToCart(int userId, int productId) throws SQLException;

	// 관심상품을 기반으로 주문 생성 (orders + orders_product에 insert), orders_id 생성 반환
	int createWishToOrder(int userId, int productId) throws SQLException;

	// 관심상품에서 선택 상품 삭제(다중 포함)
	int deleteSelectedWishes(int userId, List<String> productIdList);
	
	// 관심상품 목록 페이징 조회
	List<ProductVO> selectWishListPaging(Map<String, String> paraMap) throws SQLException;

	// 관심상품 총 개수 조회
	int getTotalWishCount(String email) throws SQLException;
	
}
