package com.github.semiprojectshop.repository.sihu.product;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String productInfo;
    private String productContents;
    private Long price;
    private long stock;
    private String productSize;
    private String matter;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductImage> productImageList;


    public enum ProductStatus {
        DELETE,
        NORMAL,
        HIDE
    }
    public static Product onlyId(Long productId) {
        Product product = new Product();
        product.productId = productId;
        return product;
    }

}
