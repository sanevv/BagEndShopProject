package com.github.semiprojectshop.web.sihu.restcontroller.product;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import com.github.semiprojectshop.service.sihu.product.ProductService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
import com.github.semiprojectshop.web.sihu.dto.product.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;
    @GetMapping("/main")
    public CustomResponse<List<MainProductResponse>> mainProductList(){
        List<MainProductResponse> mainProductList = productService.getMainProductList();
        return CustomResponse.ofOk("최신 10개 상품 검색 성공", mainProductList);
    }

    @GetMapping("/{category}")
    public CustomResponse<PaginationDto<ProductListResponse>> categoryProductList(@PathVariable String category,
                                                                                  @RequestParam (required = false) long page,
                                                                                  @RequestParam (defaultValue = "12") long size,
                                                                                  @RequestParam (required = false) String sort,
                                                                                  HttpSession session) {
        Long loginUserId = session.getAttribute("loginUser") != null ?
                (long) ((MemberVO) session.getAttribute("loginUser")).getUserId()
                : null;
        ProductListRequest productListRequest = ProductListRequest.of(page, size, sort, category);

        Optional<PaginationDto<ProductListResponse>> categoryProductList = productService.getCategoryProductList(productListRequest, loginUserId);
        return categoryProductList
                .map(paginationDto -> CustomResponse.ofOk("카테고리별 상품 검색 성공", paginationDto))
                .orElseThrow(() -> CustomMyException.fromMessage("해당 카테고리의 상품이 없습니다."));
    }

    @PutMapping("/steam")
    public CustomResponse<String> steamingProcess(@RequestParam long productId,
                                                HttpSession session){
        if(session.getAttribute("loginUser") == null)
            throw CustomMyException.fromMessage("로그인 후 찜하기를 이용해주세요.");
        long loginUserId = ((MemberVO) session.getAttribute("loginUser")).getUserId(); // TODO: 추후 로그인 기능 구현 후 수정 필요
        productService.steamingProduct(productId, loginUserId);
        return CustomResponse.ofOk("상품 찜하기 성공", "찜하기가 완료되었습니다.");

    }
    @PostMapping
    public long createProduct(@RequestParam("fk_cnum") long categoryId,
                              @RequestParam("product_name") String productName,
                              @RequestParam("stock") long stock,
                              @RequestParam("price") long price,
                              @RequestParam("product_contents") String productContents,
                              @RequestParam String productInfo,
                              @RequestParam String productSize,
                              @RequestParam String matter,
                              @RequestParam("pimage1") MultipartFile mainImage,
                              @RequestParam("files") List<MultipartFile> files){
        ProductCreateRequest request = ProductCreateRequest.of(
                categoryId,
                productName,
                stock,
                price,
                productContents,
                mainImage,
                files,
                productInfo,
                productSize,
                matter
        );


        return productService.createProduct(request);
    }

}
