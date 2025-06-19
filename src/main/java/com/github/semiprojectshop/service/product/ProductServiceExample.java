//package com.github.semiprojectshop.service.product;
//
//import com.github.semiprojectshop.repository.sihu.product.ProductRepository;
//import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
//import com.github.semiprojectshop.web.sihu.dto.product.ProductListResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ProductService {
//    private final ProductRepository productRepository;
//    public Optional<PaginationDto<ProductListResponse>> getHitProductList(long page, String specName) {
//        PaginationDto<ProductListResponse> response = productRepository.findAllProductsBySpecName(page, specName);
//
//        return null;
//    }
//}
