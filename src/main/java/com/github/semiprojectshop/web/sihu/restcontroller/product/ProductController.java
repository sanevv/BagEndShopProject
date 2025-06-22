package com.github.semiprojectshop.web.sihu.restcontroller.product;

import com.github.semiprojectshop.service.sihu.product.ProductService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/main")
    public CustomResponse<List<MainProductResponse>> mainProductList(){
        List<MainProductResponse> mainProductList = productService.getMainProductList();
        return CustomResponse.ofOk("최신 10개 상품 검색 성공", mainProductList);
    }
}
