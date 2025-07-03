package com.github.semiprojectshop.service.sihu.product;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.product.ProductImage;
import com.github.semiprojectshop.repository.sihu.product.ProductJpa;
import com.github.semiprojectshop.repository.sihu.product.wish.WishJpa;
import com.github.semiprojectshop.service.sihu.StorageService;
import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import com.github.semiprojectshop.web.sihu.dto.product.ProductCreateRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListRequest;
import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.OrderProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJpa productJpa;
    private final WishJpa wishJpa;
    private final StorageService storageService;

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

    @Transactional
    public long createProduct(ProductCreateRequest request) {
        Product product = Product.fromRequest(request);
        productJpa.save(product);
        Path path = storageService.createFileDirectory("product", product.getProductId().toString());
        String mainImageUrl = storageService.returnTheFilePathAfterTransfer(request.getMainImage(), path, "승호메롱_");
        List<String> imageUrls = request.getFiles()
                .stream().map(file->storageService.returnTheFilePathAfterTransfer(file, path))
                .toList();

        List<ProductImage> productImageList = imageUrls.stream()
                .map(url -> ProductImage.fromProductAndUrl(product, url))
                .collect(Collectors.toCollection(ArrayList::new));
        productImageList.add(ProductImage.fromProductAndMainImage(product, mainImageUrl));

        product.setProductImageList(productImageList);
        return product.getProductId();
    }
}
