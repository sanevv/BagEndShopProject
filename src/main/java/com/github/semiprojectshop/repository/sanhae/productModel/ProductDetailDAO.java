package com.github.semiprojectshop.repository.sanhae.productModel;

import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailDAO {
    // 프론트에서 가져온 ProductId로 상세페이지 보여주기
    ProductDetailVO productDetail(int productId) throws SQLException;

    // 해당 상품 추가이미지 가져오기
    List<ProductDetailVO> getProductImageList(int productId);
}
