package com.github.semiprojectshop.repository.sihu.order;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.review.Review;
import com.github.semiprojectshop.web.sihu.dto.product.order.PaymentsRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.query.Order;

@Entity
@Getter
@Table(name = "orders_product")
@DynamicInsert
public class OrdersProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersProductId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private double atDiscountRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private Long atPrice;

    public static OrdersProduct fromPaymentsRequest(PaymentsRequest paymentsRequest){
        OrdersProduct ordersProduct = new OrdersProduct();
        ordersProduct.product = Product.onlyId(paymentsRequest.getProductId());
        ordersProduct.atPrice = paymentsRequest.getAtPrice();
        ordersProduct.atDiscountRate = paymentsRequest.getAtDiscountRate();
        return ordersProduct;

    }


    // Default 접근제어자
    void settingForJoinOrder(Orders order){
        this.order = order;
    }


}
