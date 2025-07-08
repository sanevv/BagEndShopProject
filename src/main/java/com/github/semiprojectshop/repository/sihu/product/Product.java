package com.github.semiprojectshop.repository.sihu.product;

import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.web.sihu.dto.product.ProductCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
//@DynamicInsert
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

    public void addProductImage(List<ProductImage> productImageList, String contents) {
        if(this.productImageList == null)
            this.productImageList = new ArrayList<>();
        this.productImageList.addAll(productImageList);
        this.productContents = contents;
    }

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

    public static Product fromRequest(ProductCreateRequest request, long userId){
        Product product = new Product();
        product.myUser = MyUser.onlyId(userId);
        product.productName = request.getProductName();
        product.category = Category.onlyId(request.getCategoryId());
        product.stock = request.getStock();
        product.price = request.getPrice();
        product.productInfo = request.getProductInfo();
        product.productSize = request.getProductSize();
        product.matter = request.getMatter();
        product.createdAt = LocalDateTime.now();
        product.productStatus = ProductStatus.NORMAL;
        product.productContents = "temp";
        return product;
    }

}
