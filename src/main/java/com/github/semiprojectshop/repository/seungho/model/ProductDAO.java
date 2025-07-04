package com.github.semiprojectshop.repository.seungho.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.github.semiprojectshop.repository.seungho.domain.ProductVO;

public interface ProductDAO {

	ProductVO productDetailInfo(String productId) throws SQLException;

	List<String> getImgPath(String productId) throws SQLException;

	int deleteImg(Map<String, Object> paramap) throws SQLException;

	int updateProduct(ProductVO pvo) throws SQLException;

	int insertImg(Map<String, Object> paramap) throws SQLException;

}
