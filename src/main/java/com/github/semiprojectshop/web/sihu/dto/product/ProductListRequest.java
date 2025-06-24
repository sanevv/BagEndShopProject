package com.github.semiprojectshop.web.sihu.dto.product;

import lombok.Getter;
@Getter
public class ProductListRequest {
    private long page;
    private long size;
    private ProductSortRequest sort;
    private ProductCategoryRequest category;

    public static ProductListRequest of(long page, long size, String sort, String category) {
        ProductListRequest request = new ProductListRequest();
        request.page = page-1;
        request.size = size;
        request.sort = ProductSortRequest.from(sort);
        request.category = ProductCategoryRequest.from(category);
        return request;
    }


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

        private static ProductCategoryRequest from(String categoryName) {
            try{
                return ProductCategoryRequest.valueOf(categoryName.toUpperCase());
            } catch (IllegalArgumentException | NullPointerException e){
                return ALL; // 기본값으로 ALL을 반환
            }
        }

    }
    public enum ProductSortRequest {
        PRICE_ASC("가격 낮은 순"),
        PRICE_DESC("가격 높은 순"),
        NEWEST("최신순");
        @Getter
        private final String sortName;
        ProductSortRequest(String sortName) {
            this.sortName = sortName;
        }

        private static ProductSortRequest from(String sort) {
            try{
                return ProductSortRequest.valueOf(sort.toUpperCase());
            } catch (IllegalArgumentException | NullPointerException e){
                return NEWEST; // 기본값으로 최신순을 반환
            }
        }

    }

}
