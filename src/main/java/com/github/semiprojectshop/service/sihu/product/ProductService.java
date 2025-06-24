package com.github.semiprojectshop.service.sihu.product;

import com.github.semiprojectshop.repository.sihu.product.ProductJpa;
import com.github.semiprojectshop.repository.sihu.product.wish.WishJpa;
import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
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
    public Optional<PaginationDto<ProductListResponse>> getCategoryProductList(ProductListRequest productListRequest) {
        long loginUserId = 1L; // TODO: 로그인 기능 구현 후 제거
        PaginationDto<ProductListResponse> paginationDto = productJpa.findCategoryProductList(productListRequest, loginUserId);
        return Optional.ofNullable(paginationDto);
    }

    @Transactional
    public void steamingProduct(long productId, long loginUserId) {
        long modified = wishJpa.steamingProductByUserId(productId, loginUserId);
        if (modified == 0)
            throw new RuntimeException("찜하기 처리에 실패했습니다. 다시 시도해주세요.");
    }
}
