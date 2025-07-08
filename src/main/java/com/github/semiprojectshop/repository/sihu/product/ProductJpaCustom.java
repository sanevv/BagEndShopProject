package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.OrderProductRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductJpaCustom {

    List<MainProductResponse> findMainProductList();

    PaginationDto<ProductListResponse> findCategoryProductList(ProductListRequest productListRequest, Long loginUserId);

    List<CartListResponse> findProductInfoForOrder(List<OrderProductRequest> orderProductRequests);

    // tbl_map(위,경도) 테이블에 있는 정보를 가져오기(select)
	List<Map<String, String>> selectStoreMap() throws SQLException;
}
