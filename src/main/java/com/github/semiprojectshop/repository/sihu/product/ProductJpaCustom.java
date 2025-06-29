package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.OrderProductRequest;

import java.util.List;

public interface ProductJpaCustom {

    List<MainProductResponse> findMainProductList();

    PaginationDto<ProductListResponse> findCategoryProductList(ProductListRequest productListRequest, long loginUserId);

    List<CartListResponse> findProductInfoForOrder(List<OrderProductRequest> orderProductRequests);
}
