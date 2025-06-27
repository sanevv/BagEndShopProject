package com.github.semiprojectshop.repository.sanhae.productModel;

import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailDAO {
    // 프론트에서 가져온 ProductId로 상세페이지 보여주기
    ProductDetailVO ProductDetail(int productId) throws SQLException;
}
