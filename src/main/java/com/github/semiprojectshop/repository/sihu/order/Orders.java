package com.github.semiprojectshop.repository.sihu.order;

import com.github.semiprojectshop.repository.sihu.user.MyUser;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@DynamicInsert
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MyUser myUser;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private orderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersProduct> orders;

    public enum orderStatus {
        DELIVERY, CANCEL, COMPLETED, READY
    }
    public static Orders fromMyUserAndOrders(MyUser myUser, List<OrdersProduct> orders) {
        Orders order = new Orders();
        order.myUser = myUser;
        order.createdAt = LocalDateTime.now();
        order.status = orderStatus.READY; // 초기 상태는 READY로 설정
        order.orders = orders;
        for (OrdersProduct ordersProduct : orders) {
            ordersProduct.settingForJoinOrder(order); // 양방향 연관관계 설정
        }
        return order;
    }
}
