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
}
