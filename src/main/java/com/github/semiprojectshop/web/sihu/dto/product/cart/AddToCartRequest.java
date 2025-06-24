package com.github.semiprojectshop.web.sihu.dto.product.cart;

import lombok.Getter;

@Getter
public class AddToCartRequest {
    private long productId;
    private int quantity;

}
