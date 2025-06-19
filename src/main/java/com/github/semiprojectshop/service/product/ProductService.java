package com.github.semiprojectshop.service.product;

import com.github.semiprojectshop.repository.sihu.product.ProductJpa;
import com.github.semiprojectshop.web.sihu.dto.product.MainProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJpa productJpa;


    public List<MainProductResponse> getMainProductList() {
        return productJpa.findMainProductList();
    }
}
