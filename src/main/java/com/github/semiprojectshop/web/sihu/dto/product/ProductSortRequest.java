package com.github.semiprojectshop.web.sihu.dto.product;

import lombok.Getter;

public enum ProductSortRequest {
    PRICE_ASC("가격 낮은 순"),
    PRICE_DESC("가격 높은 순"),
    NEWEST("최신순");
    @Getter
    private final String sortName;
    ProductSortRequest(String sortName) {
        this.sortName = sortName;
    }

    public static ProductSortRequest from(String sort) {
        try{
            return ProductSortRequest.valueOf(sort.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e){
            return NEWEST; // 기본값으로 최신순을 반환
        }
    }

}
