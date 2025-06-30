package com.github.semiprojectshop.service.sihu.product;

import com.github.semiprojectshop.repository.sihu.product.ProductJpa;
import com.github.semiprojectshop.repository.sihu.product.wish.WishJpa;
import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.OrderProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJpa productJpa;
    private final WishJpa wishJpa;

    @Transactional(readOnly = true)
    public List<MainProductResponse> getMainProductList() {
        return productJpa.findMainProductList();
    }

    @Transactional(readOnly = true)
    public Optional<PaginationDto<ProductListResponse>> getCategoryProductList(ProductListRequest productListRequest, Long loginUserId) {

        PaginationDto<ProductListResponse> paginationDto = productJpa.findCategoryProductList(productListRequest, loginUserId);
        return Optional.ofNullable(paginationDto);
    }

    @Transactional
    public void steamingProduct(long productId, long loginUserId) {
        long modified = wishJpa.steamingProductByUserId(productId, loginUserId);
        if (modified == 0)
            throw new RuntimeException("찜하기 처리에 실패했습니다. 다시 시도해주세요.");
    }

    @Transactional(readOnly = true)
    public List<CartListResponse> getProductInfoForOrder(List<OrderProductRequest> orderProductRequests) {
        return productJpa.findProductInfoForOrder(orderProductRequests);
    }
}
