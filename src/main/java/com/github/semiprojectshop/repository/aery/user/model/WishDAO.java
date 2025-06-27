package com.github.semiprojectshop.repository.aery.user.model;

import java.sql.SQLException;
import java.util.List;

import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;

public interface WishDAO {
	
	// 로그인한 사용자가 본인의 위시리스트를 조회
	List<ProductDetailVO> selectWishListByUserId(int userId) throws SQLException;

	
	
	
}
