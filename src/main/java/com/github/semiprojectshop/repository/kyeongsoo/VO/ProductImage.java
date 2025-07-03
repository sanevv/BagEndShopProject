package com.github.semiprojectshop.repository.kyeongsoo.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class ProductImage {

    private int productId;
    private int productImageId;
    private String imagePath;
    private int thumbnail;
}
