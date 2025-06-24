package com.github.semiprojectshop.web.sihu.dto.product;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

public enum ProductCategoryRequest {
    ALL("전체"),
    MESSENGER("메신저"),
    CROSS("크로스백"),
    BACKPACK("백팩");
    @Getter
    private final String categoryName;
    ProductCategoryRequest(String categoryName) {
        this.categoryName = categoryName;
    }

    public static ProductCategoryRequest from(String categoryName) {
        try{
            return ProductCategoryRequest.valueOf(categoryName.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e){
            return ALL; // 기본값으로 ALL을 반환
        }
    }


}
