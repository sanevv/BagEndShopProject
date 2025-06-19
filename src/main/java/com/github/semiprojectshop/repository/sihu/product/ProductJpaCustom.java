package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;

import java.util.List;

public interface ProductJpaCustom {

    List<MainProductResponse> findMainProductList();
}
