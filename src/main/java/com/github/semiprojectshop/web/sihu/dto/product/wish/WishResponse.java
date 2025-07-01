package com.github.semiprojectshop.web.sihu.dto.product.wish;

import com.github.semiprojectshop.config.DiscountConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class WishResponse {
    private final long productId;
    private final String productName;
    private final String productThumbnailUrl;
    private final long productPrice;
    public double getPriceAfterDiscount(){
        return Math.round(productPrice * (1-DiscountConstants.DISCOUNT_RATE));
    }

}
