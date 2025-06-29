package com.github.semiprojectshop.web.sihu.dto.product.cart;

import com.github.semiprojectshop.config.DiscountConstants;
import lombok.Getter;
import lombok.Setter;

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
    public void settingForOrder(long productCartId, int quantity) {
        this.productCartId = productCartId;
        this.quantity = quantity;
    }
}
