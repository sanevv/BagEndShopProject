package com.github.semiprojectshop.service.sihu.product;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.product.ProductImage;
import com.github.semiprojectshop.repository.sihu.product.ProductJpa;
import com.github.semiprojectshop.repository.sihu.product.wish.WishJpa;
import com.github.semiprojectshop.service.sanhae.FtpUploadService;
import com.github.semiprojectshop.service.sanhae.exeptions.BadSanHaeException;
import com.github.semiprojectshop.service.sanhae.product.ProductImageService;
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
import org.springframework.web.multipart.MultipartFile;

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
    private final FtpUploadService ftpUploadService;
    private final ProductImageService productImageService;

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

    // full URL 반환 여부
    boolean withHost = true;
    @Transactional
    public long createProduct(ProductCreateRequest request, long userId) {
        Product product = Product.fromRequest(request, userId);
        productJpa.save(product);

        int productId = product.getProductId().intValue();

        // 메인 이미지
        String mainImageUrl = uploadOrNull(productId, request.getMainImage(), "main_", withHost);

        // 서브 이미지
        List<String> subImageUrls = request.getFiles() == null ? List.of() :
                request.getFiles().stream()
                        .filter(f -> f != null && !f.isEmpty())
                        .map(f -> uploadOrNull(productId, f, "sub_", withHost))
                        .filter(p -> p != null)
                        .toList();

        // 상세 콘텐츠(설명) 파일
        String contentsUrl = uploadOrNull(productId, request.getProductContents(), "contents_", withHost);

        // ProductImage 엔티티 목록 구성
        List<ProductImage> productImageList = new ArrayList<>();
        for (String url : subImageUrls) {
            productImageList.add(ProductImage.fromProductAndUrl(product, url));
        }
        if (mainImageUrl != null) {
            productImageList.add(ProductImage.fromProductAndMainImage(product, mainImageUrl));
        }

        product.addProductImage(productImageList, contentsUrl);
        return product.getProductId();
    }

    private String uploadOrNull(int productId, MultipartFile file, String prefix, boolean withHost) {
        try {
            return productImageService.uploadProductImage(productId, file, prefix, withHost);
        } catch (Exception e) {
            // 필요 시 로깅/롤백 전략 조정
            throw new RuntimeException("파일 업로드 실패: ", e);
        }
    }
}
