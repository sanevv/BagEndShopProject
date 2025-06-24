package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;

import java.util.List;

public interface ProductJpaCustom {

    List<MainProductResponse> findMainProductList();

    PaginationDto<ProductListResponse> findCategoryProductList(ProductListRequest productListRequest, long loginUserId);
}
