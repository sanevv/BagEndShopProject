package com.github.semiprojectshop.web.sihu.dto.product.cart;

import com.github.semiprojectshop.config.DiscountConstants;
import lombok.Getter;

@Getter
public class CartListResponse {
    private long productCartId;
    private long productId;
    private String productName;
    private String productImage;
    private long price;
    private int quantity;
    public double getDiscountRate() {
        return DiscountConstants.DISCOUNT_RATE;
    }
}
