package com.github.semiprojectshop.web.sihu.dto.product;

import com.github.semiprojectshop.config.DiscountConstants;
import lombok.Getter;

@Getter
public class ProductListResponse {
    private long productId;
    private String productName;
    private long price;
    private boolean wished;
    private String thumbnail;


    public long getDiscountRate(){
        return Math.round(DiscountConstants.DISCOUNT_RATE * 100);
    }
//    public long getSalePercentage(){
//        if (salePrice == 0) {
//            return 0;
//        }
//        //할인된 가격에 곱하기 100을 해서 원래 가격으로 나누면 할인율이 나옴
//        return (price - salePrice) * 100 / price;
//    }


}
