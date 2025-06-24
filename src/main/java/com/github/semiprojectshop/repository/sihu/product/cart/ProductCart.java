package com.github.semiprojectshop.repository.sihu.product.cart;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "product_cart")
public class ProductCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCartId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MyUser myUser;
    private int quantity;
    private LocalDateTime createdAt;

    public static ProductCart fromProductMyUserQuantity(Product product, MyUser myUser, int quantity) {
        ProductCart productCart = new ProductCart();
        productCart.product = product;
        productCart.myUser = myUser;
        productCart.quantity = quantity;
        productCart.createdAt = LocalDateTime.now();
        return productCart;
    }



}
