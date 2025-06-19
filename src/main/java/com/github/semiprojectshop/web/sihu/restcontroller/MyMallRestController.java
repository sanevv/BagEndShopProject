//package com.github.semiprojectshop.web.sihu.controller;
//
//import com.github.semiprojectshop.service.product.ProductService;
//import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
//import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
//import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/mall")
//@RequiredArgsConstructor
//public class MyMallRestController {
//    private final ProductService  productService;
//
//    @GetMapping
//    public CustomResponse<PaginationDto<ProductListResponse>> productList(@RequestParam long page, @RequestParam String specName){
//        return productService.getHitProductList(page-1, specName)
//                .filter(list -> !list.getItems().isEmpty())
//                .map(productList -> CustomResponse.ofOk("회원 목록 조회 성공", productList))
//                .orElseGet(() -> CustomResponse.emptyDataOk("회원 목록이 없습니다."));
//    }
//
//}
