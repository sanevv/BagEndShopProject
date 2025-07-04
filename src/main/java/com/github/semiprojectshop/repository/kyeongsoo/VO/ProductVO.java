package com.github.semiprojectshop.repository.kyeongsoo.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class ProductVO {

    private int productId;
    private int userId;
    private String productName;
    private String productInfo;
    private String productContents;
    private int price;
    private int stock;
    private String productSize;
    private String  matter;
    private String createdAt;
    private int categoryId;
    private String productStatus;
}
