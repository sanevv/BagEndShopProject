package com.github.semiprojectshop.repository.sihu.product;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String imagePath;
    private boolean thumbnail;

    public static ProductImage fromProductAndUrl(Product product, String url) {
        ProductImage productImage = new ProductImage();
        productImage.product = product;
        productImage.imagePath = url;
        productImage.thumbnail = false; // 기본값은 false로 설정
        return productImage;
    }
    public static ProductImage fromProductAndMainImage(Product product, String url) {
        ProductImage productImage = new ProductImage();
        productImage.product = product;
        productImage.imagePath = url;
        productImage.thumbnail = true; // 메인 이미지의 경우 thumbnail을 true로 설정
        return productImage;
    }
}
