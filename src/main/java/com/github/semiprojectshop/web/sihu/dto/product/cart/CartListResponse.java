package com.github.semiprojectshop.web.sihu.dto.product.cart;

import lombok.Getter;

@Getter
public class CartListResponse {
    private long productCartId;
    private long productId;
    private String productName;
    private String productImage;
    private int quantity;
}
