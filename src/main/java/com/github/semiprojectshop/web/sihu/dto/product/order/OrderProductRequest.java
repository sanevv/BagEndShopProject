package com.github.semiprojectshop.web.sihu.dto.product.order;

import lombok.Getter;


@Getter
public class OrderProductRequest {
    private long productId;
    private long cartId;
    private int quantity;
}
