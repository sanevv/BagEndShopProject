package com.github.semiprojectshop.web.sihu.dto.product.wish;

import com.github.semiprojectshop.config.DiscountConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class WishResponse {
    private long productId;
    private String productName;
    private String productThumbnailUrl;
    private long productPrice;
    public double getPriceAfterDiscount(){
        return Math.round(productPrice * (1-DiscountConstants.DISCOUNT_RATE));
    }

}
