package com.github.semiprojectshop.repository.kyeongsoo.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class AdminMemberProVO {

    private int orderProductId;
    private int orderId;
    private int productId;
    private int atPrice;
    private double atDiscountRate;
    private int reviewId;
    private String thumbnailPath;
    private String productName;
    private String productSize;
    private int quantity;
    private String name;
}
