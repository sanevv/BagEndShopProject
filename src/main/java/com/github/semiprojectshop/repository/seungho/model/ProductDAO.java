package com.github.semiprojectshop.repository.seungho.model;

import java.sql.SQLException;

import com.github.semiprojectshop.repository.seungho.domain.ProductVO;

public interface ProductDAO {

	ProductVO productDetailInfo(String productId) throws SQLException;

}
