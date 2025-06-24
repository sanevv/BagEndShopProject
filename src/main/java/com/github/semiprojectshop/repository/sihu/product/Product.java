package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.repository.sihu.user.MyUser;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MyUser myUser;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

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
