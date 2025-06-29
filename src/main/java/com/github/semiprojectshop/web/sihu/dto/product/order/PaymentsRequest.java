package com.github.semiprojectshop.web.sihu.dto.product.order;

import lombok.Getter;

@Getter
public class PaymentsRequest {
    private long productId;
    private long productCartId;
    private long atPrice;
    private double atDiscountRate;
    private int quantity;
}
